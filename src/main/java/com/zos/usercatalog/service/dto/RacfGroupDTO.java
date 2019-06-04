package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.RacfGroup} entity.
 */
public class RacfGroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 8)
    private String name;

    private Integer gid;


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

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RacfGroupDTO racfGroupDTO = (RacfGroupDTO) o;
        if (racfGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), racfGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RacfGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gid=" + getGid() +
            "}";
    }
}
