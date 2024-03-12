package org.omega.vktesttask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    private String method;
    private String endpoint;
    private String requestArguments;
    private String ipAddress;
}
