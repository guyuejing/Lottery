package lottery.domain.strategy.service.draw;

import lottery.domain.strategy.model.aggregates.StrategyRich;
import lottery.domain.strategy.model.vo.AwardBriefVO;
import lottery.domain.strategy.repository.IStrategyRepository;


import javax.annotation.Resource;

public class DrawStrategySupport extends DrawConfig{

    @Resource
    protected IStrategyRepository strategyRepository;

    /**
     * 查询策略配置信息
     * @param strategyId
     * @return
     */
    protected StrategyRich queryStrategyRich(Long strategyId) {
        return strategyRepository.queryStrategyRich(strategyId);
    }

    /**
     * 查询奖品详情信息
     * @param awardId
     * @return
     */
    protected AwardBriefVO queryAwardInfoByAwardId(String awardId) {
        return strategyRepository.queryAwardInfo(awardId);
    }
}
