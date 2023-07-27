package lottery.domain.award.service.goods.impl;

import lombok.extern.slf4j.Slf4j;
import lottery.common.Constants;
import lottery.domain.award.model.req.GoodsReq;
import lottery.domain.award.model.res.DistributionRes;
import lottery.domain.award.service.goods.DistributionBase;
import lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * 实物发货类商品
 */
@Component
@Slf4j
public class PhysicalGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {
        // 模拟调用实物发奖
        log.info("模拟调用实物发奖 uId：{} awardContent：{}", req.getuId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.GrantState.COMPLETE.getCode());
        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return Constants.AwardType.PhysicalGoods.getCode();
    }
}
