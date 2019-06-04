package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.RacfUser} entity.
 */
public class RacfUserDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 8)
    private String name;

    private Integer uid;

    private String type;

    private String esppRequest;

    private String asozRequest;


    private Long armId;

    private String armName;

    private Long ownerId;

    private String ownerName;

    private Set<RacfGroupDTO> groups = new HashSet<>();

    private Set<ZosSystemDTO> systems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEsppRequest() {
        return esppRequest;
    }

    public void setEsppRequest(String esppRequest) {
        this.esppRequest = esppRequest;
    }

    public String getAsozRequest() {
        return asozRequest;
    }

    public void setAsozRequest(String asozRequest) {
        this.asozRequest = asozRequest;
    }

    public Long getArmId() {
        return armId;
    }

    public void setArmId(Long armId) {
        this.armId = armId;
    }

    public String getArmName() {
        return armName;
    }

    public void setArmName(String armName) {
        this.armName = armName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Set<RacfGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(Set<RacfGroupDTO> racfGroups) {
        this.groups = racfGroups;
    }

    public Set<ZosSystemDTO> getSystems() {
        return systems;
    }

    public void setSystems(Set<ZosSystemDTO> zosSystems) {
        this.systems = zosSystems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RacfUserDTO racfUserDTO = (RacfUserDTO) o;
        if (racfUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), racfUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RacfUserDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uid=" + getUid() +
            ", type='" + getType() + "'" +
            ", esppRequest='" + getEsppRequest() + "'" +
            ", asozRequest='" + getAsozRequest() + "'" +
            ", arm=" + getArmId() +
            ", arm='" + getArmName() + "'" +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerName() + "'" +
            "}";
    }
}
