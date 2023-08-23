package lottery.domain.rule.service.engine;

import lombok.extern.slf4j.Slf4j;
import lottery.common.Constants;
import lottery.domain.rule.model.aggregates.TreeRuleRich;
import lottery.domain.rule.model.req.DecisionMatterReq;
import lottery.domain.rule.model.res.EngineResult;
import lottery.domain.rule.model.vo.TreeNodeVO;
import lottery.domain.rule.model.vo.TreeRootVO;
import lottery.domain.rule.service.logic.LogicFilter;

import java.util.Map;

/**
 * 规则引擎基础类
 */
@Slf4j
public abstract class EngineBase extends EngineConfig implements EngineFilter{


    @Override
    public EngineResult process(DecisionMatterReq matter) {
        throw new RuntimeException("未实现规则引擎服务");
    }

    protected TreeNodeVO engineDecisionMaker(TreeRuleRich treeRuleRich, DecisionMatterReq matterReq) {
        TreeRootVO treeRootVO = treeRuleRich.getTreeRoot();
        Map<Long, TreeNodeVO> treeNodeVOMap = treeRuleRich.getTreeNodeMap();

        // 规则树根ID
        Long rootNodeId = treeRootVO.getTreeRootNodeId();
        TreeNodeVO treeNodeInfo = treeNodeVOMap.get(rootNodeId);

        // NodeType: 1.非叶 2.叶子
        while (Constants.NodeType.STEM.equals(treeNodeInfo.getNodeType())) {
            String ruleKey = treeNodeInfo.getRuleKey();
            LogicFilter logicFilter = logicFilterMap.get(ruleKey);
            // 获取边界条件值
            String matterValue = logicFilter.matterValue(matterReq);
            // 找到满足条件的下一个节点
            Long nextNode = logicFilter.filter(matterValue, treeNodeInfo.getTreeNodeLineInfoList());
            treeNodeInfo = treeNodeVOMap.get(nextNode);
            log.info("决策树引擎=>{} userId：{} treeId：{} treeNode：{} ruleKey：{} matterValue：{}",
                    treeRootVO.getTreeName(), matterReq.getUserId(), matterReq.getTreeId(),
                    treeNodeInfo.getTreeNodeId(), ruleKey, matterValue);
        }
        return treeNodeInfo;
    }
}
