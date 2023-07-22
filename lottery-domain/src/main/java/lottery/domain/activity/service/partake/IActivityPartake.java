package lottery.domain.activity.service.partake;

import lottery.domain.activity.model.req.PartakeReq;
import lottery.domain.activity.model.res.PartakeResult;

/**
 * 抽奖活动参与接口
 */
public interface IActivityPartake {
    /**
     * 参与活动
     *
     * @param req 入参
     * @return 领取结果
     */
    PartakeResult doPartake(PartakeReq req);
}
