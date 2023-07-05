package lottery.infrastructure.dao;

import lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAwardDao {
    Award queryAwardInfo(String awardId);
}
