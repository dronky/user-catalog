package com.zos.usercatalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "espp_request")
    private String esppRequest;

    @Column(name = "asoz_request")
    private String asozRequest;

    @ManyToOne
    @JsonIgnoreProperties("names")
    private RacfUser racfUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsppRequest() {
        return esppRequest;
    }

    public Request esppRequest(String esppRequest) {
        this.esppRequest = esppRequest;
        return this;
    }

    public void setEsppRequest(String esppRequest) {
        this.esppRequest = esppRequest;
    }

    public String getAsozRequest() {
        return asozRequest;
    }

    public Request asozRequest(String asozRequest) {
        this.asozRequest = asozRequest;
        return this;
    }

    public void setAsozRequest(String asozRequest) {
        this.asozRequest = asozRequest;
    }

    public RacfUser getRacfUser() {
        return racfUser;
    }

    public Request racfUser(RacfUser racfUser) {
        this.racfUser = racfUser;
        return this;
    }

    public void setRacfUser(RacfUser racfUser) {
        this.racfUser = racfUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", esppRequest='" + getEsppRequest() + "'" +
            ", asozRequest='" + getAsozRequest() + "'" +
            "}";
    }
}
