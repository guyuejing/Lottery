package lottery.domain.award.service.goods.impl;

import lombok.extern.slf4j.Slf4j;
import lottery.common.Constants;
import lottery.domain.award.model.req.GoodsReq;
import lottery.domain.award.model.res.DistributionRes;
import lottery.domain.award.service.goods.DistributionBase;
import lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * 描述类商品，以文字形式展示给用户
 */
@Component
@Slf4j
public class DescGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());
        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return Constants.AwardType.DESC.getCode();
    }
}
