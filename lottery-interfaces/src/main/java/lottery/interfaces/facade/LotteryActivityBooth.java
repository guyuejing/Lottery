package lottery.interfaces.facade;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import lottery.application.process.IActivityProcess;
import lottery.application.process.req.DrawProcessReq;
import lottery.application.process.res.DrawProcessResult;
import lottery.application.process.res.RuleQuantificationCrowdResult;
import lottery.common.Constants;
import lottery.domain.rule.model.req.DecisionMatterReq;
import lottery.domain.strategy.model.vo.DrawAwardInfo;
import lottery.interfaces.assembler.IMapping;
import lottery.rpc.ILotteryActivityBooth;
import lottery.rpc.dto.AwardDTO;
import lottery.rpc.req.DrawReq;
import lottery.rpc.req.QuantificationDrawReq;
import lottery.rpc.res.DrawRes;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 抽奖活动展台
 */
@Controller
@Slf4j
public class LotteryActivityBooth implements ILotteryActivityBooth {
    @Resource
    private IActivityProcess activityProcess;
    @Resource
    private IMapping<DrawAwardInfo, AwardDTO> awardMapping;

    @Override
    public DrawRes doDraw(DrawReq drawReq) {
        try {
            log.info("抽奖开始，uid：{}， activityId：{}", drawReq.getuId(), drawReq.getActivityId());
            // 1.执行抽奖
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getuId(), drawReq.getActivityId()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                log.error("抽奖，失败（抽奖过程异常）, uid: {}, avtivityId: {}", drawReq.getuId(), drawReq.getActivityId());
                return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }
            // 2. 数据转换
            DrawAwardInfo drawAwardInfo = drawProcessResult.getDrawAwardInfo();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardInfo);
            awardDTO.setActivityId(drawReq.getActivityId());
            // 3. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("抽奖，完成 uid：{}， activityid： {} drawRes：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));
            return drawRes;
        } catch (Exception e) {
            log.error("抽奖，失败 uId：{} activityId：{} reqJson：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
            return new DrawRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }

    @Override
    public DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq) {
        try {
            log.info("量化人群抽奖，开始，uid：{}，treeId:{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
            // 1.执行规则引擎，获取用户可以参与的活动号
            RuleQuantificationCrowdResult quantificationCrowdResult = activityProcess.doRuleQuantificationCrowd(new DecisionMatterReq(quantificationDrawReq.getTreeId(), quantificationDrawReq.getuId(), quantificationDrawReq.getValMap()));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(quantificationCrowdResult.getCode())) {
                log.error("量化人群抽奖，失败(规则引擎执行异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(quantificationCrowdResult.getCode(), quantificationCrowdResult.getInfo());
            }
            // 2.执行抽奖
            Long activityId = quantificationCrowdResult.getActivityId();
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(quantificationDrawReq.getuId(), activityId));
            if (!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())) {
                log.error("量化人群抽奖，失败(抽奖过程异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }
            // 3.数据转换
            DrawAwardInfo drawAwardVO = drawProcessResult.getDrawAwardInfo();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(activityId);

            // 4. 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            log.info("量化人群抽奖，完成 uId：{} treeId：{} drawRes：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(drawRes));

            return drawRes;
        } catch (Exception e) {
            log.error("量化人群抽奖，失败 uId：{} treeId：{} reqJson：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(quantificationDrawReq), e);
            return new DrawRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }

    }
}

