package lottery.test;

import lombok.extern.slf4j.Slf4j;
import lottery.domain.strategy.model.req.DrawReq;
import lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import lottery.domain.strategy.service.draw.IDrawExec;
import lottery.infrastructure.dao.IActivityDao;
import lottery.infrastructure.dao.IStrategyDao;
import lottery.infrastructure.po.Strategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiTest {

    @Resource(name = "entiretyRateRandomDrawAlgorithm")
    private IDrawAlgorithm randomDrawAlgorithm;
//    @Reference(interfaceClass = IActivityBooth.class, url = "dubbo://127.0.0.1:20880")
//    private IActivityBooth activityBooth;

    @Resource
    private IActivityDao activityDao;
    @Resource
    private IDrawExec drawExec;

    //    @Test
//    public void test_insert() {
//        Activity activity = new Activity();
//        activity.setActivityId(100002L);
//        activity.setActivityName("测试活动");
//        activity.setActivityDesc("仅用于插入数据测试");
//        activity.setBeginDateTime(new Date());
//        activity.setEndDateTime(new Date());
//        activity.setStockCount(100);
//        activity.setTakeCount(10);
//        activity.setState(0);
//        activity.setCreator("xiaofuge");
//        activityDao.insert(activity);
//    }
    @Resource
    private IStrategyDao strategyDao;

//    @Test
//    public void test_rpc() {
//        ActivityReq req = new ActivityReq();
//        req.setActivityId(2L);
//        ActivityRes result = activityBooth.queryActivityById(req);
//        log.info("测试结果： {}", JSON.toJSONString(result));
//    }

    @Test
    public void test_select() {
        Strategy strategy = strategyDao.queryStrategy(10001L);
        System.out.println(strategy);
    }

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
    }
}
