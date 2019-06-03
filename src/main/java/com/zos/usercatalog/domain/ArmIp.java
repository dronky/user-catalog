package com.zos.usercatalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

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
    @Column(name = "ip_version")
    private IpVersion ipVersion;

    @ManyToOne
    @JsonIgnoreProperties("armIps")
    private Arm arm;

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

    public Arm getArm() {
        return arm;
    }

    public ArmIp arm(Arm arm) {
        this.arm = arm;
        return this;
    }

    public void setArm(Arm arm) {
        this.arm = arm;
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
            ", ipVersion='" + getIpVersion() + "'" +
            "}";
    }
}
