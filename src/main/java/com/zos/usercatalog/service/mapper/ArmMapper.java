package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.ArmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Arm} and its DTO {@link ArmDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArmIpMapper.class})
public interface ArmMapper extends EntityMapper<ArmDTO, Arm> {


    @Mapping(target = "names", ignore = true)
    Arm toEntity(ArmDTO armDTO);

    default Arm fromId(Long id) {
        if (id == null) {
            return null;
        }
        Arm arm = new Arm();
        arm.setId(id);
        return arm;
    }
}
