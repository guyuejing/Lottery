package lottery.domain.activity.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lottery.domain.activity.model.aggregates.ActivityConfigRich;

/**
 * 活动配置请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityConfigReq {

    /** 活动ID */
    private Long activityId;

    /** 活动配置信息 */
    private ActivityConfigRich activityConfigRich;

}
