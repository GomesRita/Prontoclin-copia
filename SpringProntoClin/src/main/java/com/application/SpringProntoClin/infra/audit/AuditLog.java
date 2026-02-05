package com.application.SpringProntoClin.infra.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "username")
    private String username;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "query")
    private String query;

    @Column(name = "status")
    private Integer status;

    @Column(name = "duration_ms")
    private Long durationMs;

    @Lob
    @Column(name = "request_body")
    private String requestBody;
}
