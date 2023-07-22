package lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 用户活动参与次数表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTakeActivityCount {
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private String uId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 可领取次数
     */
    private Integer totalCount;
    /**
     * 剩余可领取次数
     */
    private Integer leftCount;
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
    public String getuId(){
        return this.uId;
    }
}
