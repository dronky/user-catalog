package com.zos.usercatalog.service.mapper;

import com.zos.usercatalog.domain.*;
import com.zos.usercatalog.service.dto.RacfUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RacfUser} and its DTO {@link RacfUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {OwnerMapper.class, ArmMapper.class, RacfGroupMapper.class, SystemMapper.class})
public interface RacfUserMapper extends EntityMapper<RacfUserDTO, RacfUser> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "arm.id", target = "armId")
    @Mapping(source = "arm.name", target = "armName")
    RacfUserDTO toDto(RacfUser racfUser);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "armId", target = "arm")
    @Mapping(target = "names", ignore = true)
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
