package com.example.employer.exeptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Generated;

import java.beans.ConstructorProperties;

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
        if (o == this) {
            return true;
        } else if (!(o instanceof ErrorResponseBody)) {
            return false;
        } else {
            ErrorResponseBody other = (ErrorResponseBody) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71:
                {
                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status == null) {
                            break label71;
                        }
                    } else if (this$status.equals(other$status)) {
                        break label71;
                    }

                    return false;
                }

                Object this$reason = this.getReason();
                Object other$reason = other.getReason();
                if (this$reason == null) {
                    if (other$reason != null) {
                        return false;
                    }
                } else if (!this$reason.equals(other$reason)) {
                    return false;
                }

                label57:
                {
                    Object this$publicMessage = this.getPublicMessage();
                    Object other$publicMessage = other.getPublicMessage();
                    if (this$publicMessage == null) {
                        if (other$publicMessage == null) {
                            break label57;
                        }
                    } else if (this$publicMessage.equals(other$publicMessage)) {
                        break label57;
                    }

                    return false;
                }

                Object this$incident = this.getIncident();
                Object other$incident = other.getIncident();
                if (this$incident == null) {
                    if (other$incident != null) {
                        return false;
                    }
                } else if (!this$incident.equals(other$incident)) {
                    return false;
                }
                return false;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof ErrorResponseBody;
    }

    @Generated
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $reason = this.getReason();
        result = result * 59 + ($reason == null ? 43 : $reason.hashCode());
        Object $publicMessage = this.getPublicMessage();
        result = result * 59 + ($publicMessage == null ? 43 : $publicMessage.hashCode());
        Object $incident = this.getIncident();
        result = result * 59 + ($incident == null ? 43 : $incident.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        Integer var10000 = this.getStatus();
        return "ErrorResponseBody(status=" + var10000 + ", reason=" + this.getReason() + ", publicMessage=" + this.getPublicMessage() + ", incident=" + this.getIncident() + ")";
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
