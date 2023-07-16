package lottery.domain.activity.model.aggregates;

import lottery.domain.activity.model.vo.ActivityVO;
import lottery.domain.activity.model.vo.AwardVO;
import lottery.domain.activity.model.vo.StrategyVO;

import java.util.List;

/**
 * 活动配置聚合信息
 */
public class ActivityConfigRich {

    /** 活动配置 */
    private ActivityVO activity;

    /** 策略配置(含策略明细) */
    private StrategyVO strategy;

    /** 奖品配置 */
    private List<AwardVO> awardList;

}
