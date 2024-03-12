package org.omega.vktesttask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omega.vktesttask.entity.AuditEntity;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO {
    private long id;
    private Instant date;
    private UserDTO user;
    private String method;
    private String endpoint;
    private String requestArguments;
    private String ipAddress;

    public AuditDTO(AuditEntity auditEntity) {
        this.id = auditEntity.getId();
        this.date = auditEntity.getDate();
        this.user = new UserDTO(auditEntity.getUser());
        this.method = auditEntity.getMethod();
        this.endpoint = auditEntity.getEndpoint();
        this.requestArguments = auditEntity.getRequestArguments();
        this.ipAddress = auditEntity.getIpAddress();
    }
}
