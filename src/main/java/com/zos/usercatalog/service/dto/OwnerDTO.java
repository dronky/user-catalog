package com.zos.usercatalog.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.Owner} entity.
 */
public class OwnerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String familyName;

    private String patronymic;

    private String phone;


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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OwnerDTO ownerDTO = (OwnerDTO) o;
        if (ownerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ownerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OwnerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", patronymic='" + getPatronymic() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
