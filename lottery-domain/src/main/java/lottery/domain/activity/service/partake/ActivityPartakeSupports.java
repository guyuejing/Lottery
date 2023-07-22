package lottery.domain.activity.service.partake;

import lottery.domain.activity.model.req.PartakeReq;
import lottery.domain.activity.model.vo.ActivityBillVO;
import lottery.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;

/**
 * 活动领取模操作 一些通用的数据服务
 */
public class ActivityPartakeSupports {
    @Resource
    protected IActivityRepository activityRepository;

    protected ActivityBillVO queryActivityBill(PartakeReq req){
        return activityRepository.queryActivityBill(req);
    }
}
