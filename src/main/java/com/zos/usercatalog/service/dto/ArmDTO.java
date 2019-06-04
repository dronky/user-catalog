package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.Arm} entity.
 */
public class ArmDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String prodIp;

    private String extraProdIp;

    private String testIp;

    private String extraTestIp;


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

    public String getProdIp() {
        return prodIp;
    }

    public void setProdIp(String prodIp) {
        this.prodIp = prodIp;
    }

    public String getExtraProdIp() {
        return extraProdIp;
    }

    public void setExtraProdIp(String extraProdIp) {
        this.extraProdIp = extraProdIp;
    }

    public String getTestIp() {
        return testIp;
    }

    public void setTestIp(String testIp) {
        this.testIp = testIp;
    }

    public String getExtraTestIp() {
        return extraTestIp;
    }

    public void setExtraTestIp(String extraTestIp) {
        this.extraTestIp = extraTestIp;
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
            ", prodIp='" + getProdIp() + "'" +
            ", extraProdIp='" + getExtraProdIp() + "'" +
            ", testIp='" + getTestIp() + "'" +
            ", extraTestIp='" + getExtraTestIp() + "'" +
            "}";
    }
}
