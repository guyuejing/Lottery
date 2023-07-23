package lottery.test;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lottery.application.process.IActivityProcess;
import lottery.application.process.req.DrawProcessReq;
import lottery.application.process.res.DrawProcessResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.logging.Logger;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityProcessTest {

    @Resource
    private IActivityProcess activityProcess;

    @Test
    public void test_doDrawProcess() {
        DrawProcessReq req = new DrawProcessReq();
        req.setuId("guyuejing");
        req.setActivityId(100001L);
        DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(req);
        log.info("请求入参：{}", JSON.toJSONString(req));
        log.info("测试结果： {}", JSON.toJSONString(drawProcessResult));
    }

}
