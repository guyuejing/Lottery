package lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户领取活动表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTakeActivity {
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private String uId;
    /**
     * 活动领取ID
     */
    private Long takeId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动领取时间
     */
    private Timestamp takeDate;
    /**
     * 领取次数
     */
    private Integer takeCount;
    /**
     * 策略ID
     */
    private Long strategyId;

    /**
     * 活动单使用状态 0未使用、1已使用
     * Constants.TaskState
     */
    private Integer state;
    /**
     * 防重ID
     */
    private String uuid;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuId() {
        return this.uId;
    }
}
