package lottery.rpc;

import lottery.rpc.req.ActivityReq;
import lottery.rpc.res.ActivityRes;

public interface IActivityBooth {

    ActivityRes queryActivityById(ActivityReq req);
}
