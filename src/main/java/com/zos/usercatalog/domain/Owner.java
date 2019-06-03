package com.zos.usercatalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Owner.
 */
@Entity
@Table(name = "owner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "owner")
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone")
    private String phone;

    @OneToOne(mappedBy = "owner")
    @JsonIgnore
    private RacfUser name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Owner name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Owner familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Owner patronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public Owner phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RacfUser getName() {
        return name;
    }

    public Owner name(RacfUser racfUser) {
        this.name = racfUser;
        return this;
    }

    public void setName(RacfUser racfUser) {
        this.name = racfUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Owner)) {
            return false;
        }
        return id != null && id.equals(((Owner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Owner{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", familyName='" + getFamilyName() + "'" +
            ", patronymic='" + getPatronymic() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
