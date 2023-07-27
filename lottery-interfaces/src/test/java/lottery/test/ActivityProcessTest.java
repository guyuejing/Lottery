package lottery.test;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lottery.application.mq.producer.KafkaProducer;
import lottery.application.process.IActivityProcess;
import lottery.application.process.req.DrawProcessReq;
import lottery.application.process.res.DrawProcessResult;
import lottery.domain.rule.model.req.DecisionMatterReq;
import lottery.domain.rule.model.res.EngineResult;
import lottery.domain.rule.service.engine.EngineFilter;
import lottery.infrastructure.dao.IAwardDao;
import lottery.infrastructure.po.Award;
import lottery.rpc.ILotteryActivityBooth;
import lottery.rpc.req.DrawReq;
import lottery.rpc.req.QuantificationDrawReq;
import lottery.rpc.res.DrawRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityProcessTest {

    @Resource
    private IActivityProcess activityProcess;

    @Resource
    private EngineFilter engineFilter;

    @Resource
    private ILotteryActivityBooth lotteryActivityBooth;
    @Resource
    private IAwardDao awardDao;

    @Resource
    private KafkaProducer kafkaProducer;
    @Test
    public void test_doDrawProcess() {
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("hujing");
        req.setActivityId(100001L);
        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);
        log.info("请求入参：{}", JSON.toJSONString(req));
        log.info("测试结果： {}", JSON.toJSONString(drawProcessResult));
    }

    @Test
    public void test_award() {
        Award award = awardDao.queryAwardInfo("5");
        log.info(award.toString());
    }

    @Test
    public void test_process() {
        DecisionMatterReq matterReq = new DecisionMatterReq();
        matterReq.setTreeId(2110081902L);
        matterReq.setUserId("guyue");
        matterReq.setValMap(new HashMap<String, Object>(){{
            put("gender", "man");
            put("age", 26);
        }});
        EngineResult result = engineFilter.process(matterReq);
        log.info("请求参数： {}",JSON.toJSONString(matterReq));
        log.info("测试结果： {}",JSON.toJSONString(result));
    }

    @Test
    public void test_do_draw() {
        DrawReq drawReq = new DrawReq();
        drawReq.setuId("guyue");
        drawReq.setActivityId(100001L);
        DrawRes drawRes = lotteryActivityBooth.doDraw(drawReq);
        log.info("请求参数：{}", JSON.toJSONString(drawReq));
        log.info("测试结果：{}", JSON.toJSONString(drawRes));
    }

    @Test
    public void test_doQuantificationDraw() {
        QuantificationDrawReq req = new QuantificationDrawReq();
        req.setuId("xiaohu");
        req.setTreeId(2110081902L);
        req.setValMap(new HashMap<String, Object>(){{
            put("gender", "man");
            put("age", "18");
        }});
        DrawRes drawRes = lotteryActivityBooth.doQuantificationDraw(req);
        log.info("请求参数： {}", JSON.toJSONString(req));
        log.info("测试结果： {}", JSON.toJSONString(drawRes));
    }

    @Test
    public void test_do_DrawProcess() throws InterruptedException{
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("xiaohutongxue");
        req.setActivityId(100001L);
        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);
        log.info("请求入参： {}", JSON.toJSONString(req));
        log.info("测试结果： {}", JSON.toJSONString(drawProcessResult));
    }
}
