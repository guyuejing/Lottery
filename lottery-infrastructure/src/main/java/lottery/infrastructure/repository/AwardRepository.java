package lottery.infrastructure.repository;

import lottery.domain.award.repository.IAwardRepository;
import lottery.infrastructure.dao.IUserStrategyExportDao;
import lottery.infrastructure.po.UserStrategyExport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class AwardRepository implements IAwardRepository {
    @Resource
    private IUserStrategyExportDao userStrategyExportDao;
    @Override
    public void updateUserAwardState(String uId, Long orderId, String awardId, Integer grantState) {
        UserStrategyExport userStrategyExport = new UserStrategyExport();
        userStrategyExport.setuId(uId);
        userStrategyExport.setOrderId(orderId);
        userStrategyExport.setAwardId(awardId);
        userStrategyExport.setGrantState(grantState);
        userStrategyExportDao.updateUserAwardState(userStrategyExport);
    }
}
