package com.zos.usercatalog.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zos.usercatalog.domain.Request} entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    private String esppRequest;

    private String asozRequest;


    private Long racfUserId;

    private String racfUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEsppRequest() {
        return esppRequest;
    }

    public void setEsppRequest(String esppRequest) {
        this.esppRequest = esppRequest;
    }

    public String getAsozRequest() {
        return asozRequest;
    }

    public void setAsozRequest(String asozRequest) {
        this.asozRequest = asozRequest;
    }

    public Long getRacfUserId() {
        return racfUserId;
    }

    public void setRacfUserId(Long racfUserId) {
        this.racfUserId = racfUserId;
    }

    public String getRacfUserName() {
        return racfUserName;
    }

    public void setRacfUserName(String racfUserName) {
        this.racfUserName = racfUserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (requestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", esppRequest='" + getEsppRequest() + "'" +
            ", asozRequest='" + getAsozRequest() + "'" +
            ", racfUser=" + getRacfUserId() +
            ", racfUser='" + getRacfUserName() + "'" +
            "}";
    }
}
