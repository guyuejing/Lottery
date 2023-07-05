package lottery.domain.strategy.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lottery.common.Constants;
import lottery.domain.strategy.model.vo.DrawAwardInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrawResult {
    private String uId;

    // 策略ID
    private Long strategyId;

    /**
     * 中奖状态：0未中奖、1已中奖、2兜底奖 Constants.DrawState
     */
    private Integer drawState = Constants.DrawState.FAIL.getCode();

    /**
     * 中奖奖品信息
     */
    private DrawAwardInfo drawAwardInfo;

    public DrawResult(String uId, Long strategyId, Integer drawState) {
        this.uId = uId;
        this.strategyId = strategyId;
        this.drawState = drawState;
    }
}
