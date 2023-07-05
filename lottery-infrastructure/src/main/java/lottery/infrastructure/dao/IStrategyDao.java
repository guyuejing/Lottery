package lottery.infrastructure.dao;

import lottery.infrastructure.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IStrategyDao {
    Strategy queryStrategy(Long strategyId);
}
