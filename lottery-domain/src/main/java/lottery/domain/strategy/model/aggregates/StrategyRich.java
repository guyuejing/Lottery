package lottery.domain.strategy.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lottery.domain.strategy.model.vo.StrategyBriefVO;
import lottery.domain.strategy.model.vo.StrategyDetailBriefVO;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyRich {
    private Long strategyId;
    // 策略配置
    private StrategyBriefVO strategy;
    // 策略明细
    private List<StrategyDetailBriefVO> strategyDetailList;
}
