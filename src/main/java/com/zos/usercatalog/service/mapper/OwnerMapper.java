package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.OwnerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {


    @Mapping(target = "racfUser", ignore = true)
    Owner toEntity(OwnerDTO ownerDTO);

    default Owner fromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner owner = new Owner();
        owner.setId(id);
        return owner;
    }
}
