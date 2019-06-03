package com.zos.usercatalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A RacfUser.
 */
@Entity
@Table(name = "racf_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "racfuser")
public class RacfUser implements Serializable {

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

    @Column(name = "jhi_uid")
    private Integer uid;

    @Column(name = "jhi_type")
    private String type;

    @OneToOne
    @JoinColumn(unique = true)
    private Owner owner;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private Arm arm;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "racf_user_group",
               joinColumns = @JoinColumn(name = "racf_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<RacfGroup> groups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "racf_user_system",
               joinColumns = @JoinColumn(name = "racf_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "system_id", referencedColumnName = "id"))
    private Set<System> systems = new HashSet<>();

    @OneToMany(mappedBy = "racfUser")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Request> names = new HashSet<>();

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

    public RacfUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public RacfUser uid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public RacfUser type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Owner getOwner() {
        return owner;
    }

    public RacfUser owner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Arm getArm() {
        return arm;
    }

    public RacfUser arm(Arm arm) {
        this.arm = arm;
        return this;
    }

    public void setArm(Arm arm) {
        this.arm = arm;
    }

    public Set<RacfGroup> getGroups() {
        return groups;
    }

    public RacfUser groups(Set<RacfGroup> racfGroups) {
        this.groups = racfGroups;
        return this;
    }

    public RacfUser addGroup(RacfGroup racfGroup) {
        this.groups.add(racfGroup);
        racfGroup.getNames().add(this);
        return this;
    }

    public RacfUser removeGroup(RacfGroup racfGroup) {
        this.groups.remove(racfGroup);
        racfGroup.getNames().remove(this);
        return this;
    }

    public void setGroups(Set<RacfGroup> racfGroups) {
        this.groups = racfGroups;
    }

    public Set<System> getSystems() {
        return systems;
    }

    public RacfUser systems(Set<System> systems) {
        this.systems = systems;
        return this;
    }

    public RacfUser addSystem(System system) {
        this.systems.add(system);
        system.getNames().add(this);
        return this;
    }

    public RacfUser removeSystem(System system) {
        this.systems.remove(system);
        system.getNames().remove(this);
        return this;
    }

    public void setSystems(Set<System> systems) {
        this.systems = systems;
    }

    public Set<Request> getNames() {
        return names;
    }

    public RacfUser names(Set<Request> requests) {
        this.names = requests;
        return this;
    }

    public RacfUser addName(Request request) {
        this.names.add(request);
        request.setRacfUser(this);
        return this;
    }

    public RacfUser removeName(Request request) {
        this.names.remove(request);
        request.setRacfUser(null);
        return this;
    }

    public void setNames(Set<Request> requests) {
        this.names = requests;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RacfUser)) {
            return false;
        }
        return id != null && id.equals(((RacfUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RacfUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uid=" + getUid() +
            ", type='" + getType() + "'" +
            "}";
    }
}
