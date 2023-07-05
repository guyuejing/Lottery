package lottery.domain.strategy.service.draw;

import lottery.domain.strategy.model.req.DrawReq;
import lottery.domain.strategy.model.res.DrawResult;

public interface IDrawExec {
    DrawResult doDrawExec(DrawReq req);
}
