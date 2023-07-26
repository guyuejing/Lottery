package lottery.interfaces.assembler;

import lottery.domain.strategy.model.vo.DrawAwardInfo;
import lottery.rpc.dto.AwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardInfo, AwardDTO>{

    @Override
    AwardDTO sourceToTarget(DrawAwardInfo var1);

    @Override
    DrawAwardInfo targetToSource(AwardDTO var1);
}
