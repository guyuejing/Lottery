package lottery.domain.rule.service.logic;

import lottery.common.Constants;
import lottery.domain.rule.model.req.DecisionMatterReq;
import lottery.domain.rule.model.vo.TreeNodeLineVO;

import java.util.List;

/**
 * 规则基础抽象类
 */
public abstract class BaseLogic implements LogicFilter{
    @Override
    public Long filter(String matterValue, List<TreeNodeLineVO> treeNodeLineInfoList) {
        for (TreeNodeLineVO treeNodeLineVO : treeNodeLineInfoList) {
            if (decisionLogic(matterValue, treeNodeLineVO)) {
                return treeNodeLineVO.getNodeIdTo();
            }
        }
        return Constants.Global.TREE_NULL_NODE;
    }

    /**
     * 获取规则比对值
     * @param decisionMatter 决策物料
     * @return 比对值
     */
    @Override
    public abstract String matterValue(DecisionMatterReq decisionMatter);

    private boolean decisionLogic(String matterValue, TreeNodeLineVO nodeLineVO) {
        switch (nodeLineVO.getRuleLimitType()) {
            case Constants.RuleLimitType.EQUAL:
                return matterValue.equals(nodeLineVO.getRuleLimitValue());
            case Constants.RuleLimitType.GT:
                return Double.parseDouble(matterValue) > Double.parseDouble(nodeLineVO.getRuleLimitValue());
            case Constants.RuleLimitType.LT:
                return Double.parseDouble(matterValue) < Double.parseDouble(nodeLineVO.getRuleLimitValue());
            case Constants.RuleLimitType.GE:
                return Double.parseDouble(matterValue) >= Double.parseDouble(nodeLineVO.getRuleLimitValue());
            case Constants.RuleLimitType.LE:
                return Double.parseDouble(matterValue) <= Double.parseDouble(nodeLineVO.getRuleLimitValue());
            default:
                return false;
        }
    }
}
