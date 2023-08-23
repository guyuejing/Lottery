package lottery.infrastructure.repository;

import lottery.common.Constants;
import lottery.domain.rule.model.aggregates.TreeRuleRich;
import lottery.domain.rule.model.vo.TreeNodeLineVO;
import lottery.domain.rule.model.vo.TreeNodeVO;
import lottery.domain.rule.model.vo.TreeRootVO;
import lottery.domain.rule.repository.IRuleRepository;
import lottery.infrastructure.dao.RuleTreeDao;
import lottery.infrastructure.dao.RuleTreeNodeDao;
import lottery.infrastructure.dao.RuleTreeNodeLineDao;
import lottery.infrastructure.po.RuleTree;
import lottery.infrastructure.po.RuleTreeNode;
import lottery.infrastructure.po.RuleTreeNodeLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RuleRepository implements IRuleRepository {

    @Resource
    private RuleTreeDao ruleTreeDao;
    @Resource
    private RuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;


    @Override
    public TreeRuleRich queryTreeRuleRich(Long treeId) {
        // 规则树
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        TreeRootVO treeRootVO = new TreeRootVO();
        treeRootVO.setTreeId(ruleTree.getId());
        treeRootVO.setTreeRootNodeId(ruleTree.getTreeRootNodeId());
        treeRootVO.setTreeName(ruleTree.getTreeName());

        // 树节点 -> 树之间的边
        Map<Long, TreeNodeVO> treeNodeMap = new HashMap<>();
        // 查询这棵树的所有节点
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeList(treeId);
        for (RuleTreeNode ruleTreeNode : ruleTreeNodes) {
            // 存放每个节点 所对应的所有边 封装成TreeNodeLineVO
            List<TreeNodeLineVO> treeNodeLineVOList = new ArrayList<>();
            if (Constants.NodeType.STEM.equals(ruleTreeNode.getNodeType())) {
                RuleTreeNodeLine ruleTreeNodeLineReq = new RuleTreeNodeLine();
                ruleTreeNodeLineReq.setTreeId(treeId);
                ruleTreeNodeLineReq.setNodeIdFrom(ruleTreeNode.getId());
                // 查询该节点的所有边
                List<RuleTreeNodeLine> ruleTreeNodeLineList = ruleTreeNodeLineDao.queryRuleTreeNodeLineList(ruleTreeNodeLineReq);
                for (RuleTreeNodeLine nodeLine : ruleTreeNodeLineList) {
                    TreeNodeLineVO treeNodeLineInfo = new TreeNodeLineVO();
                    treeNodeLineInfo.setNodeIdFrom(nodeLine.getNodeIdFrom());
                    treeNodeLineInfo.setNodeIdTo(nodeLine.getNodeIdTo());
                    treeNodeLineInfo.setRuleLimitType(nodeLine.getRuleLimitType());
                    treeNodeLineInfo.setRuleLimitValue(nodeLine.getRuleLimitValue());
                    treeNodeLineVOList.add(treeNodeLineInfo);
                }
            }
            TreeNodeVO treeNodeInfo = new TreeNodeVO();
            treeNodeInfo.setTreeId(ruleTreeNode.getTreeId());
            treeNodeInfo.setTreeNodeId(ruleTreeNode.getId());
            treeNodeInfo.setNodeType(ruleTreeNode.getNodeType());
            treeNodeInfo.setNodeValue(ruleTreeNode.getNodeValue());
            treeNodeInfo.setRuleKey(ruleTreeNode.getRuleKey());
            treeNodeInfo.setRuleDesc(ruleTreeNode.getRuleDesc());
            treeNodeInfo.setTreeNodeLineInfoList(treeNodeLineVOList);

            treeNodeMap.put(ruleTreeNode.getId(), treeNodeInfo);
        }
        TreeRuleRich treeRuleRich = new TreeRuleRich();
        treeRuleRich.setTreeRoot(treeRootVO);
        treeRuleRich.setTreeNodeMap(treeNodeMap);

        return treeRuleRich;
    }
}
