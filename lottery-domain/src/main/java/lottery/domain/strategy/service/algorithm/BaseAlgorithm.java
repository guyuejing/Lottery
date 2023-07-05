package lottery.domain.strategy.service.algorithm;

import lottery.domain.strategy.model.vo.AwardRateInfo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class BaseAlgorithm implements IDrawAlgorithm {

    // 黄金分割点
    private final int HASH_INCR = 0x61c88647;

    private final int RATE_TUPLE_LENGTH = 128;

    // 存放概率与奖品对应的散列结果  strategyId -> rateTuple
    protected Map<Long, String[]> rateTupleMap = new ConcurrentHashMap<>();

    // 奖品区间概率值，strategyId -> [awardId->begin、awardId->end]
    protected Map<Long, List<AwardRateInfo>> awardRateInfoMap = new ConcurrentHashMap<>();

    @Override
    public void initRateTuple(Long strategyId, List<AwardRateInfo> awardRateInfoList) {
        awardRateInfoMap.put(strategyId, awardRateInfoList);

        String[] rateTuple = rateTupleMap.computeIfAbsent(strategyId, k -> new String[RATE_TUPLE_LENGTH]);
        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : awardRateInfoList) {
            int rateVal = awardRateInfo.getAwardRate().multiply(new BigDecimal(100)).intValue();
            // 循环填充概率范围值  比如0.3的概率中奖 -> 0.2~0.5 -> 20~50  然后21到50的30个位置哈希分配到数组之中
            for (int i = cursorVal + 1; i <= (rateVal + cursorVal); i++) {
                rateTuple[hashIdx(i)] = awardRateInfo.getAwardId();
            }
            cursorVal += rateVal;
        }
    }

    @Override
    public boolean isExistRateTuple(Long strategyId) {
        return rateTupleMap.containsKey(strategyId);
    }


    protected int hashIdx(int val) {
        int hashCode = val & HASH_INCR + HASH_INCR;
        return hashCode & (RATE_TUPLE_LENGTH - 1);
    }

    /**
     * 生成百位随机码
     * @param bound
     * @return
     */
     protected int generateSecureRandomIntCode (int bound) {
        return new SecureRandom().nextInt(bound) + 1;
     }
}
