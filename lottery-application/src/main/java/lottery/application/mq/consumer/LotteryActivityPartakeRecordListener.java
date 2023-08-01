package lottery.application.mq.consumer;

import com.alibaba.fastjson.JSON;
import lottery.domain.activity.model.vo.ActivityPartakeRecordVO;
import lottery.domain.activity.service.partake.IActivityPartake;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 抽奖活动领取记录监听
 */
@Component
public class LotteryActivityPartakeRecordListener {

    private Logger logger = LoggerFactory.getLogger(LotteryActivityPartakeRecordListener.class);

    @Resource
    private IActivityPartake activityPartake;

    public void onMessage(ConsumerRecord<?,?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC)String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (!message.isPresent()) {
            return;
        }
        ActivityPartakeRecordVO activityPartakeRecordVO = JSON.parseObject((String) message.get(), ActivityPartakeRecordVO.class);
        logger.info("消费MQ消息，异步扣减活动库存 message：{}", message.get());
        // 3. 更新数据库库存【实际场景业务体量较大，可能也会由于MQ消费引起并发，对数据库产生压力，所以如果并发量较大，可以把库存记录缓存中，并使用定时任务进行处理缓存和数据库库存同步，减少对数据库的操作次数】
        activityPartake.updateActivityStock(activityPartakeRecordVO);
    }

}
