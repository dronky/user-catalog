package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.Arm} entity.
 */
public class ArmDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    private Set<ArmIpDTO> armIps = new HashSet<>();

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

    public Set<ArmIpDTO> getArmIps() {
        return armIps;
    }

    public void setArmIps(Set<ArmIpDTO> armIps) {
        this.armIps = armIps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArmDTO armDTO = (ArmDTO) o;
        if (armDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), armDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArmDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
