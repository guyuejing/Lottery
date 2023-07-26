package lottery.domain.rule.service.engine.impl;

import lottery.domain.rule.model.aggregates.TreeRuleRich;
import lottery.domain.rule.model.req.DecisionMatterReq;
import lottery.domain.rule.model.res.EngineResult;
import lottery.domain.rule.model.vo.TreeNodeVO;
import lottery.domain.rule.repository.IRuleRepository;
import lottery.domain.rule.service.engine.EngineBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ruleEngineHandler")
public class RuleEngineHandler extends EngineBase {

    @Resource
    private IRuleRepository ruleRepository;

    @Override
    public EngineResult process(DecisionMatterReq matterReq) {
        // 决策规则树
        TreeRuleRich treeRuleRich = ruleRepository.queryTreeRuleRich(matterReq.getTreeId());
        if (null == treeRuleRich) {
            throw new RuntimeException("Tree Rule is null");
        }
        // 决策节点
        TreeNodeVO treeNodeInfo = engineDecisionMaker(treeRuleRich, matterReq);
        // 决策结果
        return new EngineResult(matterReq.getUserId(), treeNodeInfo.getTreeId(),treeNodeInfo.getTreeNodeId(), treeNodeInfo.getNodeValue());
    }
}
