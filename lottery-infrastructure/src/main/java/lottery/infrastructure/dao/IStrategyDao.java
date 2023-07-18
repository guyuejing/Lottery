package lottery.infrastructure.dao;

import lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IStrategyDao {
    Strategy queryStrategy(Long strategyId);
    /**
     * 插入策略配置
     *
     * @param req 策略配置
     */
    void insertStrategy(Strategy req);
}
