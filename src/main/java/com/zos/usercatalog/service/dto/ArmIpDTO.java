package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.zos.usercatalog.domain.enumeration.IpVersion;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.ArmIp} entity.
 */
public class ArmIpDTO implements Serializable {

    private Long id;

    @NotNull
    private String ip;

    private IpVersion ipVersion;


    private Long armId;

    private String armIp;

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

    public IpVersion getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(IpVersion ipVersion) {
        this.ipVersion = ipVersion;
    }

    public Long getArmId() {
        return armId;
    }

    public void setArmId(Long armId) {
        this.armId = armId;
    }

    public String getArmIp() {
        return armIp;
    }

    public void setArmIp(String armIp) {
        this.armIp = armIp;
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
            ", ipVersion='" + getIpVersion() + "'" +
            ", arm=" + getArmId() +
            ", arm='" + getArmIp() + "'" +
            "}";
    }
}