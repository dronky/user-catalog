package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.ZosSystem} entity.
 */
public class ZosSystemDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Size(max = 39)
    private String ip;


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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZosSystemDTO zosSystemDTO = (ZosSystemDTO) o;
        if (zosSystemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zosSystemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZosSystemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ip='" + getIp() + "'" +
            "}";
    }
}
