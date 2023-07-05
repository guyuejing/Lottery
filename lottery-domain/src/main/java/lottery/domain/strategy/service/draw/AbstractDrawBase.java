package lottery.domain.strategy.service.draw;

import lombok.extern.slf4j.Slf4j;
import lottery.common.Constants;
import lottery.domain.strategy.model.aggregates.StrategyRich;
import lottery.domain.strategy.model.req.DrawReq;
import lottery.domain.strategy.model.res.DrawResult;
import lottery.domain.strategy.model.vo.AwardRateInfo;
import lottery.domain.strategy.model.vo.DrawAwardInfo;
import lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import lottery.infrastructure.po.Award;
import lottery.infrastructure.po.Strategy;
import lottery.infrastructure.po.StrategyDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义抽奖过程 模板设计模式
 */
@Slf4j
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec {

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        // 获取抽奖策略
        StrategyRich strategyRich = super.queryStrategyRich(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();

        // 校验抽奖策略是否已经初始化到内存
        this.checkAndInitRateData(req.getStrategyId(), strategy.getStrategyMode(), strategyRich.getStrategyDetailList());

        // 获取不在抽奖范围的列表 包括：奖品库存为空、风控策略、临时调整等
        List<String> excludeAwardIds = this.queryExcludeAwardIds(req.getStrategyId());

        // 抽奖
        String awardId = this.drawAlgorithm(req.getStrategyId(), drawAlgorithmMap.get(strategy.getStrategyMode()), excludeAwardIds);

        // 包装中奖结果
        return this.buildDrawResult(req.getUId(), strategy.getStrategyId(), awardId);
    }

    /**
     * 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等，这类数据是含有业务逻辑的，所以需要由具体的实现方决定
     *
     * @param strategyId 策略ID
     * @return 排除的奖品ID集合
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * 执行抽奖算法
     *
     * @param strategyId      策略ID
     * @param drawAlgorithm   抽奖算法模型
     * @param excludeAwardIds 排除的抽奖ID集合
     * @return 中奖奖品ID
     */
    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds);

    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        // 如果不是整体概率不需要check
        if (!Constants.StrategyMode.SINGLE.getCode().equals(strategyMode)) {
            return;
        }
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);

        boolean existRateTuple = drawAlgorithm.isExistRateTuple(strategyId);
        if (existRateTuple) {
            return;
        }

        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);

    }

    private DrawResult buildDrawResult(String uId, Long strategyId, String awardId) {
        if (null == awardId) {
            log.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawResult(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }

        Award award = super.queryAwardInfoByAwardId(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo(award.getAwardId(), award.getAwardName());
        log.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());

        return new DrawResult(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }


}
