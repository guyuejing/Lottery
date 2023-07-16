package lottery.domain.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 变更活动状态对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterStateVO {

    /** 活动ID */
    private Long activityId;

    /** 更新前状态 */
    private Integer beforeState;

    /** 更新后状态 */
    private Integer afterState;
}
