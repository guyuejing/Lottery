package lottery;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lottery.rpc.IActivityBooth;
import lottery.rpc.req.ActivityReq;
import lottery.rpc.res.ActivityRes;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class APITest {

    @Reference(interfaceClass = IActivityBooth.class, url = "dubbo://127.0.0.1:20880")
    private IActivityBooth activityBooth;

    @Test
    public void test_rpc() {
        ActivityReq req = new ActivityReq();
        req.setActivityId(1L);
        ActivityRes res = activityBooth.queryActivityById(req);
        log.info("测试结果： {}", JSON.toJSONString(res));
    }

}
