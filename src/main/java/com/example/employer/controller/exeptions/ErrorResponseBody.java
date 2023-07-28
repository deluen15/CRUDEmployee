package com.example.employer.controller.exeptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Generated;

import java.beans.ConstructorProperties;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponseBody {
    private Integer status;
    private String reason;
    private String publicMessage;
    private String incident;

    @Generated
    public Integer getStatus() {
        return this.status;
    }

    @Generated
    public String getReason() {
        return this.reason;
    }

    @Generated
    public String getPublicMessage() {
        return this.publicMessage;
    }

    @Generated
    public String getIncident() {
        return this.incident;
    }

    @Generated
    public void setStatus(final Integer status) {
        this.status = status;
    }

    @Generated
    public void setReason(final String reason) {
        this.reason = reason;
    }

    @Generated
    public void setPublicMessage(final String publicMessage) {
        this.publicMessage = publicMessage;
    }

    @Generated
    public void setIncident(final String incident) {
        this.incident = incident;
    }

    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorResponseBody other)) {
            return false;
        }
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(getStatus(), other.getStatus()) &&
                Objects.equals(getReason(), other.getReason()) &&
                Objects.equals(getPublicMessage(), other.getPublicMessage()) &&
                Objects.equals(getIncident(), other.getIncident());
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof ErrorResponseBody;
    }

    @Generated
    public int hashCode() {
        return Objects.hash(getStatus(), getReason(), getPublicMessage(), getIncident());
    }

    @Generated
    public String toString() {
        return String.format("ErrorResponseBody(status=%d, reason=%s, publicMessage=%s, incident=%s)",
                this.getStatus(), this.getReason(), this.getPublicMessage(), this.getIncident());
    }

    @ConstructorProperties({"status", "reason", "publicMessage", "incident"})
    @Generated
    public ErrorResponseBody(final Integer status, final String reason, final String publicMessage, final String incident) {
        this.status = status;
        this.reason = reason;
        this.publicMessage = publicMessage;
        this.incident = incident;
    }
}
