package lottery.domain.rule.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则树线信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeLineVO {

    /** 节点From */
    private Long nodeIdFrom;
    /** 节点To */
    private Long nodeIdTo;
    /** 限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围] */
    private Integer ruleLimitType;
    /** 限定值 */
    private String ruleLimitValue;
}
