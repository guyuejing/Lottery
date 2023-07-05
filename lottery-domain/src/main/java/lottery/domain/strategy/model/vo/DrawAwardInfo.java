package lottery.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrawAwardInfo {
    /**
     * 奖品ID
     */
    private String rewardId;

    /**
     * 奖品名称
     */
    private String awardName;
}
