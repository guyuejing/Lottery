package lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyDetail {
    private Long id;

    // 策略ID
    private Long strategyId;

    // 奖品ID
    private String awardId;

    private String awardNmae;
    // 奖品数量
    private String awardCount;

    // 中奖概率
    private BigDecimal awardRate;

    // 创建时间
    private Timestamp createTime;

    // 修改时间
    private Timestamp updateTime;

    private Integer awardSurplusCount;

}
