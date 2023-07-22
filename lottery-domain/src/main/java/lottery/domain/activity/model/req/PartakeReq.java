package lottery.domain.activity.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 参与活动请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartakeReq {

    /**
     * 用户ID
     */
    private String uId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动领取时间
     */
    private Timestamp partakeDate;

    public PartakeReq(String uId, long activityId) {
        this.uId = uId;
        this.activityId = activityId;
    }

    public String getuId() {
        return this.uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
