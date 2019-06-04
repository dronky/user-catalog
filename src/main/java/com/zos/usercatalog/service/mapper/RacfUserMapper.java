package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.RacfUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RacfUser} and its DTO {@link RacfUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArmMapper.class, OwnerMapper.class, RacfGroupMapper.class, ZosSystemMapper.class})
public interface RacfUserMapper extends EntityMapper<RacfUserDTO, RacfUser> {

    @Mapping(source = "arm.id", target = "armId")
    @Mapping(source = "arm.name", target = "armName")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    RacfUserDTO toDto(RacfUser racfUser);

    @Mapping(source = "armId", target = "arm")
    @Mapping(source = "ownerId", target = "owner")
    RacfUser toEntity(RacfUserDTO racfUserDTO);

    default RacfUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        RacfUser racfUser = new RacfUser();
        racfUser.setId(id);
        return racfUser;
    }
}
