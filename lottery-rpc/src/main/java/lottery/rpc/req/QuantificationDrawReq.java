package lottery.rpc.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 量化人群抽奖请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantificationDrawReq implements Serializable {
    /** 用户ID */
    private String uId;
    /** 规则树ID */
    private Long treeId;
    /** 决策值 */
    private Map<String, Object> valMap;
    public String getuId() {
        return this.uId;
    }
    public void setuId(String uId) {
        this.uId = uId;
    }
}
