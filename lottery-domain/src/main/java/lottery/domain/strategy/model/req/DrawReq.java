package lottery.domain.strategy.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrawReq {
    private String uId;

    // 策略ID
    private Long strategyId;
    /**
     * 防重ID
     */
    private String uuid;
}
