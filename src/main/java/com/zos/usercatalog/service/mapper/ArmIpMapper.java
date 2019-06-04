package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.ArmIpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmIp} and its DTO {@link ArmIpDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArmIpMapper extends EntityMapper<ArmIpDTO, ArmIp> {


    @Mapping(target = "arms", ignore = true)
    ArmIp toEntity(ArmIpDTO armIpDTO);

    default ArmIp fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArmIp armIp = new ArmIp();
        armIp.setId(id);
        return armIp;
    }
}
