package lottery.domain.strategy.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lottery.infrastructure.po.Strategy;
import lottery.infrastructure.po.StrategyDetail;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyRich {
    private Long strategyId;
    // 策略配置
    private Strategy strategy;
    // 策略明细
    private List<StrategyDetail> strategyDetailList;
}
