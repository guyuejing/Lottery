package lottery.domain.rule.service.logic.impl;

import lottery.domain.rule.model.req.DecisionMatterReq;
import lottery.domain.rule.service.logic.BaseLogic;
import org.springframework.stereotype.Component;

/**
 * 性别规则过滤器
 */
@Component
public class UserGenderFilter extends BaseLogic {
    @Override
    public String matterValue(DecisionMatterReq decisionMatter) {
        return decisionMatter.getValMap().get("gender").toString();
    }
}
