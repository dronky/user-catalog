package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.SystemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link System} and its DTO {@link SystemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemMapper extends EntityMapper<SystemDTO, System> {


    @Mapping(target = "names", ignore = true)
    System toEntity(SystemDTO systemDTO);

    default System fromId(Long id) {
        if (id == null) {
            return null;
        }
        System system = new System();
        system.setId(id);
        return system;
    }
}
