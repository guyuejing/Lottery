package lottery.infrastructure.repository;

import lottery.domain.strategy.model.aggregates.StrategyRich;
import lottery.domain.strategy.model.vo.AwardBriefVO;
import lottery.domain.strategy.model.vo.StrategyBriefVO;
import lottery.domain.strategy.model.vo.StrategyDetailBriefVO;
import lottery.domain.strategy.repository.IStrategyRepository;
import lottery.infrastructure.dao.IAwardDao;
import lottery.infrastructure.dao.IStrategyDao;
import lottery.infrastructure.dao.IStrategyDetailDao;
import lottery.infrastructure.po.Award;
import lottery.infrastructure.po.Strategy;
import lottery.infrastructure.po.StrategyDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class StrategyRepository implements IStrategyRepository {


    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = strategyDao.queryStrategy(strategyId);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.queryStrategyDetailList(strategyId);
        StrategyBriefVO strategyBriefVO = new StrategyBriefVO();
        List<StrategyDetailBriefVO> strategyBriefVOS = new ArrayList<>();
        BeanUtils.copyProperties(strategy, strategyBriefVO);
        for (StrategyDetail detail : strategyDetailList) {
            StrategyDetailBriefVO strategyDetailBriefVO = new StrategyDetailBriefVO();
            BeanUtils.copyProperties(detail, strategyDetailBriefVO);
            strategyBriefVOS.add(strategyDetailBriefVO);
        }
        return new StrategyRich(strategyId, strategyBriefVO, strategyBriefVOS);
    }

    @Override
    public AwardBriefVO queryAwardInfo(String awardId) {
        Award award = awardDao.queryAwardInfo(awardId);
        AwardBriefVO awardBriefVO = new AwardBriefVO();
        BeanUtils.copyProperties(award, awardBriefVO);
        return awardBriefVO;
    }

    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        return strategyDetailDao.queryNoStockStrategyAwardList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        StrategyDetail req = new StrategyDetail();
        req.setStrategyId(strategyId);
        req.setAwardId(awardId);
        int count = strategyDetailDao.deductStock(req);
        return count == 1;
    }
}
