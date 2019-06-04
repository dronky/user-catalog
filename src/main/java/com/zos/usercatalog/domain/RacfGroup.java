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
 * A RacfGroup.
 */
@Entity
@Table(name = "racf_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "racfgroup")
public class RacfGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 8)
    @Column(name = "name", length = 8, nullable = false, unique = true)
    private String name;

    @Column(name = "gid")
    private Integer gid;

    @ManyToMany(mappedBy = "groups")
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

    public RacfGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGid() {
        return gid;
    }

    public RacfGroup gid(Integer gid) {
        this.gid = gid;
        return this;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Set<RacfUser> getNames() {
        return names;
    }

    public RacfGroup names(Set<RacfUser> racfUsers) {
        this.names = racfUsers;
        return this;
    }

    public RacfGroup addName(RacfUser racfUser) {
        this.names.add(racfUser);
        racfUser.getGroups().add(this);
        return this;
    }

    public RacfGroup removeName(RacfUser racfUser) {
        this.names.remove(racfUser);
        racfUser.getGroups().remove(this);
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
        if (!(o instanceof RacfGroup)) {
            return false;
        }
        return id != null && id.equals(((RacfGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RacfGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gid=" + getGid() +
            "}";
    }
}
