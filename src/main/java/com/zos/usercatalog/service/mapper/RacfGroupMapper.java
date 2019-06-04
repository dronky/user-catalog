package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.RacfGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RacfGroup} and its DTO {@link RacfGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RacfGroupMapper extends EntityMapper<RacfGroupDTO, RacfGroup> {


    @Mapping(target = "names", ignore = true)
    RacfGroup toEntity(RacfGroupDTO racfGroupDTO);

    default RacfGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        RacfGroup racfGroup = new RacfGroup();
        racfGroup.setId(id);
        return racfGroup;
    }
}
