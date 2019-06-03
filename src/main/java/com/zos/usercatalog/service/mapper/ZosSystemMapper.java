package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.ZosSystemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ZosSystem} and its DTO {@link ZosSystemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZosSystemMapper extends EntityMapper<ZosSystemDTO, ZosSystem> {


    @Mapping(target = "names", ignore = true)
    ZosSystem toEntity(ZosSystemDTO zosSystemDTO);

    default ZosSystem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ZosSystem zosSystem = new ZosSystem();
        zosSystem.setId(id);
        return zosSystem;
    }
}
