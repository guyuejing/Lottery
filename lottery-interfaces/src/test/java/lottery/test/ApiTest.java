package lottery.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lottery.common.Constants;
import lottery.domain.award.model.req.GoodsReq;
import lottery.domain.award.model.res.DistributionRes;
import lottery.domain.award.service.factory.DistributionGoodsFactory;
import lottery.domain.award.service.goods.IDistributionGoods;
import lottery.domain.strategy.model.req.DrawReq;
import lottery.domain.strategy.model.res.DrawResult;
import lottery.domain.strategy.model.vo.DrawAwardInfo;
import lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import lottery.domain.strategy.service.draw.IDrawExec;
import lottery.domain.strategy.service.draw.impl.DrawExecImpl;
import lottery.infrastructure.dao.IActivityDao;
import lottery.infrastructure.po.Activity;
import lottery.rpc.IActivityBooth;
import lottery.rpc.req.ActivityReq;
import lottery.rpc.res.ActivityRes;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @Test
    public void test_drawExec() {
        drawExec.doDrawExec(new DrawReq("guyuejing", 10001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
//        drawExec.doDrawExec(new DrawReq("德玛", 10002L));
    }

    @Test
    public void test_award() {
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq("guyuejing", 10001L));
        Integer drawState = drawResult.getDrawState();
        if (Constants.DrawState.FAIL.getCode().equals(drawState)) {
            log.info("未中奖， DrawAwardInfo is null");
            return;
        }
        DrawAwardInfo drawAwardInfo = drawResult.getDrawAwardInfo();
        GoodsReq goodsReq = new GoodsReq(drawResult.getUId(), "2109313442431",
                drawAwardInfo.getAwardId(), drawAwardInfo.getAwardName(), drawAwardInfo.getAwardContent());

        // 根据奖品类型从抽奖工厂中获取对应的发奖服务
        IDistributionGoods goodsService = distributionGoodsFactory.getDistributionGoodsService(drawAwardInfo.getAwardType());
        DistributionRes res = goodsService.doDistribution(goodsReq);
        log.info("测试结果： {}", JSON.toJSONString(res));

    }
}
