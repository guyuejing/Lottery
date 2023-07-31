package lottery.application.mq.consumer;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import lottery.common.Constants;
import lottery.domain.activity.model.vo.InvoiceVO;
import lottery.domain.award.model.req.GoodsReq;
import lottery.domain.award.model.res.DistributionRes;
import lottery.domain.award.service.factory.DistributionGoodsFactory;
import lottery.domain.award.service.goods.IDistributionGoods;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class LotteryInvoiceListener {

    private Logger logger = LoggerFactory.getLogger(LotteryInvoiceListener.class);

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;
    @KafkaListener(topics = "lottery_invoice", groupId = "lottery")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
        Optional<?> message = Optional.ofNullable(record.value());
        if (!message.isPresent()) {
            return;
        }
        // 处理MQ消息
        try {
            // 转化对象
            InvoiceVO invoiceVO = JSON.parseObject((String) message.get(), InvoiceVO.class);
            // 获取发奖奖品工厂，执行发奖
            IDistributionGoods service = distributionGoodsFactory.getDistributionGoodsService(invoiceVO.getAwardType());
            DistributionRes distributionRes = service.doDistribution(new GoodsReq(invoiceVO.getuId(), invoiceVO.getOrderId(), invoiceVO.getAwardId(), invoiceVO.getAwardName(), invoiceVO.getAwardContent()));
            Assert.isTrue(Constants.AwardState.SUCCESS.getCode().equals(distributionRes.getCode()));
            // 打印日志
            logger.info("消费MQ消息，完成 topic：{} bizId：{} 发奖结果：{}", topic, invoiceVO.getuId(), JSON.toJSONString(distributionRes));
            ack.acknowledge();
        }catch (Exception e) {
            logger.error("消费MQ消息，失败 topic：{} message：{}", topic, message.get());
            throw e;
        }
    }
}
