package lottery.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award {
    // TODO 需要增加每件商品的库存字段
    private Long id;

    // 奖品ID
    private String awardId;

    // 奖品类型（文字描述、兑换码、优惠券、实物奖品暂无）
    private Integer awardType;

    // 奖品名称
    private String awardName;

    // 奖品内容「文字描述、Key、码」
    private String awardContent;

    // 创建时间
    private Timestamp createTime;

    // 修改时间
    private Timestamp updateTime;
}
