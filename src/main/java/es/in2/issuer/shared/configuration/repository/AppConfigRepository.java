package es.in2.issuer.shared.configuration.repository;

import es.in2.issuer.shared.configuration.model.AppConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppConfigRepository extends CrudRepository<AppConfig, UUID> {
    Optional<AppConfig> findByConfigKey(String configKey);
}
