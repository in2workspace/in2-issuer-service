package es.in2.issuer.repository;

import es.in2.issuer.model.entity.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, String> {
    Optional<Tenant> findByTenantDomain(String tenantDomain);
}
