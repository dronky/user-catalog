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
 * A ZosSystem.
 */
@Entity
@Table(name = "zos_system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "zossystem")
public class ZosSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 39)
    @Column(name = "ip", length = 39)
    private String ip;

    @ManyToMany(mappedBy = "systems")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
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

    public ZosSystem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public ZosSystem ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Set<RacfUser> getNames() {
        return names;
    }

    public ZosSystem names(Set<RacfUser> racfUsers) {
        this.names = racfUsers;
        return this;
    }

    public ZosSystem addName(RacfUser racfUser) {
        this.names.add(racfUser);
        racfUser.getSystems().add(this);
        return this;
    }

    public ZosSystem removeName(RacfUser racfUser) {
        this.names.remove(racfUser);
        racfUser.getSystems().remove(this);
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
        if (!(o instanceof ZosSystem)) {
            return false;
        }
        return id != null && id.equals(((ZosSystem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ZosSystem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ip='" + getIp() + "'" +
            "}";
    }
}
