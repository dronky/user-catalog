package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zos.usercatalog.domain.enumeration.Type;
import com.zos.usercatalog.domain.enumeration.IpVersion;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.ArmIp} entity.
 */
public class ArmIpDTO implements Serializable {

    private Long id;

    @NotNull
    private String ip;

    private Type type;

    private IpVersion ipVersion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public IpVersion getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(IpVersion ipVersion) {
        this.ipVersion = ipVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArmIpDTO armIpDTO = (ArmIpDTO) o;
        if (armIpDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), armIpDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArmIpDTO{" +
            "id=" + getId() +
            ", ip='" + getIp() + "'" +
            ", type='" + getType() + "'" +
            ", ipVersion='" + getIpVersion() + "'" +
            "}";
    }
}
