package lottery.domain.strategy.service.algorithm.impl;

import lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Component("singleRateRandomDrawAlgorithm")
public class SingleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        assert rateTuple != null;
        int randomVal = new SecureRandom().nextInt(100) + 1;
        int idx = super.hashIdx(randomVal);
        String awardId = rateTuple[idx];
        if (excludeAwardIds.contains(awardId)) {
            return "未中奖";
        }
        return awardId;
    }
}
