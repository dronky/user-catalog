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

import com.zos.usercatalog.domain.enumeration.Type;

import com.zos.usercatalog.domain.enumeration.IpVersion;

/**
 * A ArmIp.
 */
@Entity
@Table(name = "arm_ip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "armip")
public class ArmIp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "ip", nullable = false)
    private String ip;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "ip_version")
    private IpVersion ipVersion;

    @ManyToMany(mappedBy = "armIps")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Arm> arms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public ArmIp ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Type getType() {
        return type;
    }

    public ArmIp type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public IpVersion getIpVersion() {
        return ipVersion;
    }

    public ArmIp ipVersion(IpVersion ipVersion) {
        this.ipVersion = ipVersion;
        return this;
    }

    public void setIpVersion(IpVersion ipVersion) {
        this.ipVersion = ipVersion;
    }

    public Set<Arm> getArms() {
        return arms;
    }

    public ArmIp arms(Set<Arm> arms) {
        this.arms = arms;
        return this;
    }

    public ArmIp addArm(Arm arm) {
        this.arms.add(arm);
        arm.getArmIps().add(this);
        return this;
    }

    public ArmIp removeArm(Arm arm) {
        this.arms.remove(arm);
        arm.getArmIps().remove(this);
        return this;
    }

    public void setArms(Set<Arm> arms) {
        this.arms = arms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmIp)) {
            return false;
        }
        return id != null && id.equals(((ArmIp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ArmIp{" +
            "id=" + getId() +
            ", ip='" + getIp() + "'" +
            ", type='" + getType() + "'" +
            ", ipVersion='" + getIpVersion() + "'" +
            "}";
    }
}
