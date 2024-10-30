package es.in2.issuer.backoffice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    @Id
    private UUID tenantId;

    @Column(nullable = false)
    private String tenantName;

    @Column(unique = true, nullable = false)
    private String tenantDomain;

}
