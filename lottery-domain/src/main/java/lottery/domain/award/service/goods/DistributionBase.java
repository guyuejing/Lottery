package lottery.domain.award.service.goods;

import lombok.extern.slf4j.Slf4j;
import lottery.domain.award.repository.IAwardRepository;

import javax.annotation.Resource;

/**
 * 配送货物基础共用类
 */
@Slf4j
public class DistributionBase {

    @Resource
    private IAwardRepository awardRepository;

    protected void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        awardRepository.updateUserAwardState(uId, orderId, awardId, grantState);
    }
}
