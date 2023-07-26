package lottery.rpc;

import lottery.rpc.req.DrawReq;
import lottery.rpc.req.QuantificationDrawReq;
import lottery.rpc.res.DrawRes;


public interface ILotteryActivityBooth {

    /**
     * 指定活动抽奖
     * @param drawReq 请求参数
     * @return        抽奖结果
     */
    DrawRes doDraw(DrawReq drawReq);

    /**
     * 量化人群抽奖
     * @param quantificationDrawReq 请求参数
     * @return                      抽奖结果
     */
    DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);
}
