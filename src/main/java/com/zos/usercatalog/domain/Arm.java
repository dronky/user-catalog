package com.zos.usercatalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Arm.
 */
@Entity
@Table(name = "arm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "arm")
public class Arm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "arm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ArmIp> armIps = new HashSet<>();

    @OneToMany(mappedBy = "arm")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RacfUser> names = new HashSet<>();

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

    public Arm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ArmIp> getArmIps() {
        return armIps;
    }

    public Arm armIps(Set<ArmIp> armIps) {
        this.armIps = armIps;
        return this;
    }

    public Arm addArmIp(ArmIp armIp) {
        this.armIps.add(armIp);
        armIp.setArm(this);
        return this;
    }

    public Arm removeArmIp(ArmIp armIp) {
        this.armIps.remove(armIp);
        armIp.setArm(null);
        return this;
    }

    public void setArmIps(Set<ArmIp> armIps) {
        this.armIps = armIps;
    }

    public Set<RacfUser> getNames() {
        return names;
    }

    public Arm names(Set<RacfUser> racfUsers) {
        this.names = racfUsers;
        return this;
    }

    public Arm addName(RacfUser racfUser) {
        this.names.add(racfUser);
        racfUser.setArm(this);
        return this;
    }

    public Arm removeName(RacfUser racfUser) {
        this.names.remove(racfUser);
        racfUser.setArm(null);
        return this;
    }

    public void setNames(Set<RacfUser> racfUsers) {
        this.names = racfUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Arm)) {
            return false;
        }
        return id != null && id.equals(((Arm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Arm{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
