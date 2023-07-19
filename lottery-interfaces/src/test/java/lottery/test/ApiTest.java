package lottery.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lottery.common.Constants;
import lottery.domain.activity.model.aggregates.ActivityConfigRich;
import lottery.domain.activity.model.req.ActivityConfigReq;
import lottery.domain.activity.model.vo.ActivityVO;
import lottery.domain.activity.model.vo.AwardVO;
import lottery.domain.activity.model.vo.StrategyDetailVO;
import lottery.domain.activity.model.vo.StrategyVO;
import lottery.domain.activity.service.deploy.IActivityDeploy;
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
import lottery.domain.support.ids.IIDGenerator;
import lottery.infrastructure.dao.IActivityDao;
import lottery.infrastructure.dao.IAwardDao;
import lottery.infrastructure.dao.IStrategyDao;
import lottery.infrastructure.dao.IStrategyDetailDao;
import lottery.infrastructure.po.Activity;
import lottery.infrastructure.po.Strategy;
import lottery.infrastructure.po.StrategyDetail;
import lottery.rpc.IActivityBooth;
import lottery.rpc.req.ActivityReq;
import lottery.rpc.res.ActivityRes;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private IStrategyDao strategyDao;
    @Resource
    private IAwardDao awardDao;
    @Resource
    private IStrategyDetailDao strategyDetailDao;
    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;

    @Resource
    private Map<Constants.Ids, IIDGenerator> idGeneratorMap;
    @Resource
    private IActivityDeploy activityDeploy;
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
    @Test
    public void test_deploy() {

        StrategyVO strategy = new StrategyVO();
        strategy.setStrategyId(10003L);
        strategy.setStrategyDesc("抽奖策略");
        strategy.setStrategyMode(Constants.StrategyMode.SINGLE.getCode());
        strategy.setGrantType(1);
//        strategy.setGrantDate(new Date());
        strategy.setExtInfo("");

        StrategyDetailVO strategyDetail_01 = new StrategyDetailVO();
        strategyDetail_01.setStrategyId(strategy.getStrategyId());
        strategyDetail_01.setAwardId("101");
        strategyDetail_01.setAwardName("一等奖");
        strategyDetail_01.setAwardCount(10);
        strategyDetail_01.setAwardSurplusCount(10);
        strategyDetail_01.setAwardRate(new BigDecimal("0.05"));

        StrategyDetailVO strategyDetail_02 = new StrategyDetailVO();
        strategyDetail_02.setStrategyId(strategy.getStrategyId());
        strategyDetail_02.setAwardId("102");
        strategyDetail_02.setAwardName("二等奖");
        strategyDetail_02.setAwardCount(20);
        strategyDetail_02.setAwardSurplusCount(20);
        strategyDetail_02.setAwardRate(new BigDecimal("0.15"));

        StrategyDetailVO strategyDetail_03 = new StrategyDetailVO();
        strategyDetail_03.setStrategyId(strategy.getStrategyId());
        strategyDetail_03.setAwardId("103");
        strategyDetail_03.setAwardName("三等奖");
        strategyDetail_03.setAwardCount(50);
        strategyDetail_03.setAwardSurplusCount(10);
        strategyDetail_03.setAwardRate(new BigDecimal("0.20"));

        StrategyDetailVO strategyDetail_04 = new StrategyDetailVO();
        strategyDetail_04.setStrategyId(strategy.getStrategyId());
        strategyDetail_04.setAwardId("104");
        strategyDetail_04.setAwardName("四等奖");
        strategyDetail_04.setAwardCount(50);
        strategyDetail_04.setAwardSurplusCount(10);
        strategyDetail_04.setAwardRate(new BigDecimal("0.25"));

        StrategyDetailVO strategyDetail_05 = new StrategyDetailVO();
        strategyDetail_05.setStrategyId(strategy.getStrategyId());
        strategyDetail_05.setAwardId("105");
        strategyDetail_05.setAwardName("五等奖");
        strategyDetail_05.setAwardCount(50);
        strategyDetail_05.setAwardSurplusCount(10);
        strategyDetail_05.setAwardRate(new BigDecimal("0.35"));
        // 详细策略配置 List<StrategyDetailVO>
        List<StrategyDetailVO> strategyDetailList = new ArrayList<>();
        strategyDetailList.add(strategyDetail_01);
        strategyDetailList.add(strategyDetail_02);
        strategyDetailList.add(strategyDetail_03);
        strategyDetailList.add(strategyDetail_04);
        strategyDetailList.add(strategyDetail_05);
        strategy.setStrategyDetailList(strategyDetailList);

        ActivityVO activity = new ActivityVO();
        activity.setActivityId(100003L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("测试活动描述");
//        activity.setBeginDateTime((Timestamp) new Date());
//        activity.setEndDateTime((Timestamp) new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(Constants.ActivityState.EDIT.getCode());
        activity.setCreator("guyuejing");
        // List<AwardVO>配置
        List<AwardVO> awardVOList = new ArrayList<>();
        AwardVO awardVO1 = new AwardVO();
        awardVO1.setAwardId("101");
        awardVO1.setAwardType(1);
        awardVO1.setAwardName("一等奖");
        awardVO1.setAwardContent("test1");

        AwardVO awardVO2 = new AwardVO();
        awardVO2.setAwardId("102");
        awardVO2.setAwardType(2);
        awardVO2.setAwardName("二等奖");
        awardVO2.setAwardContent("test2");

        AwardVO awardVO3 = new AwardVO();
        awardVO3.setAwardId("103");
        awardVO3.setAwardType(1);
        awardVO3.setAwardName("三等奖");
        awardVO3.setAwardContent("test3");

        AwardVO awardVO4 = new AwardVO();
        awardVO4.setAwardId("104");
        awardVO4.setAwardType(1);
        awardVO4.setAwardName("四等奖");
        awardVO4.setAwardContent("test4");

        AwardVO awardVO5 = new AwardVO();
        awardVO5.setAwardId("105");
        awardVO5.setAwardType(1);
        awardVO5.setAwardName("五等奖");
        awardVO5.setAwardContent("test5");
        awardVOList.add(awardVO1);
        awardVOList.add(awardVO2);
        awardVOList.add(awardVO3);
        awardVOList.add(awardVO4);
        awardVOList.add(awardVO5);
        ActivityConfigRich activityConfigRich = new ActivityConfigRich();
        activityConfigRich.setActivity(activity);
        activityConfigRich.setAwardList(awardVOList);
        activityConfigRich.setStrategy(strategy);
        activityDeploy.createActivity(new ActivityConfigReq(10003L, activityConfigRich));
    }

    @Test
    public void test_id_generate() {
        log.info("雪花算法策略，生成id： {}", idGeneratorMap.get(Constants.Ids.SnowFlake).nextId());
        log.info("日期算法策略，生成id： {}", idGeneratorMap.get(Constants.Ids.ShortCode).nextId());
        log.info("随机算法策略，生成id： {}", idGeneratorMap.get(Constants.Ids.RandomNumeric).nextId());

    }


}
