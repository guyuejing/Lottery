package lottery.domain.strategy.service.algorithm.impl;

import lottery.domain.strategy.model.vo.AwardRateInfo;
import lottery.domain.strategy.service.algorithm.BaseAlgorithm;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

// 这种是百分百中奖模式
@Component("entiretyRateRandomDrawAlgorithm")
public class EntireRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        BigDecimal differenceDenominator = BigDecimal.ZERO;

        // 排除掉不在抽奖范围的奖品ID
        List<AwardRateInfo> differenceAwardRateList = new ArrayList<>();
        List<AwardRateInfo> awardRateIntervalValList = awardRateInfoMap.get(strategyId);
        for (AwardRateInfo awardRateInfo : awardRateIntervalValList) {
            String awardId = awardRateInfo.getAwardId();
            if (excludeAwardIds.contains(awardId)) {
                continue;
            }
            differenceAwardRateList.add(awardRateInfo);
            differenceDenominator = awardRateInfo.getAwardRate().add(differenceDenominator);
        }
        // 已经没有奖品了
        if (differenceAwardRateList.size() == 0) {
            return "";
        } else if (differenceAwardRateList.size() == 1) {
            return differenceAwardRateList.get(0).getAwardId();
        }
        // 获取随机概率值
        SecureRandom secureRandom = new SecureRandom();
        int randomVal = secureRandom.nextInt(100) + 1;
        String award = "";
        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : differenceAwardRateList) {
            // 更新概率
            int rateVal = awardRateInfo.getAwardRate().divide(differenceDenominator, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            if (randomVal <= cursorVal + rateVal) {
                award = awardRateInfo.getAwardId();
                break;
            }
        }
        return award;
    }
}
