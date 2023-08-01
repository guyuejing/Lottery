package lottery.application.mq.producer;

import com.alibaba.fastjson.JSON;
import lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import lottery.domain.activity.model.vo.InvoiceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

@Component
public class KafkaProducer {
    private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * MQ主题：中奖发货单
     *
     * 启动zk：bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
     * 启动kafka：bin/kafka-server-start.sh -daemon config/server.properties
     * 创建topic：bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic lottery_invoice
     */
    public static final String TOPIC_INVOICE = "lottery_invoice";

    /**
     * mq topic ：活动领取记录
     */
    public static final String TOPIC_ACTIVITY_PARTAKE = "lottery_activity_partake";
    /**
     * 发送中奖单
     * @param invoiceVO
     * @return
     */
    public ListenableFuture<SendResult<String, Object>> sendLotteryInvoice(InvoiceVO invoiceVO) {
        String sendData = JSON.toJSONString(invoiceVO);
        logger.info("发送MQ消息： topic: {}, bizId:{}, message:{}", TOPIC_INVOICE, invoiceVO.getuId(), sendData);
        return kafkaTemplate.send(TOPIC_INVOICE, sendData);
    }

    public ListenableFuture<SendResult<String, Object>> sendLotteryActivityPartakeRecord(ActivityPartakeRecordVO activityPartakeRecord) {
        String objJson = JSON.toJSONString(activityPartakeRecord);
        logger.info("发送MQ消息（领取活动记录） topic：{} bizId： {} message：{}", TOPIC_ACTIVITY_PARTAKE, activityPartakeRecord.getuId(),objJson);
        return kafkaTemplate.send(TOPIC_ACTIVITY_PARTAKE, objJson);
    }
}
