package es.in2.issuer.shared.configuration.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DefaultConfigInitializer {

    private static final String DEFAULT_TENANT = "tenant1";

    @Bean
    public CommandLineRunner loadDefaultConfigurations(JdbcTemplate jdbcTemplate) {
        return args -> {
            // Define the SQL query to insert the default configurations
            String sqlInsert = "INSERT INTO issuers.configurations (tenant_id, config_key, config_value) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT (tenant_id, config_key) DO NOTHING";

            // TENANT 1 (test purposes)
            // Issuer UI Configurations
            jdbcTemplate.update(sqlInsert, DEFAULT_TENANT, "issuer_ui_url", "http://localhost:4200");
            // Keycloak Configurations
            jdbcTemplate.update(sqlInsert, DEFAULT_TENANT, "keycloak_url", "http://localhost:9090");
            jdbcTemplate.update(sqlInsert, DEFAULT_TENANT, "keycloak_realm", "issuer");
            jdbcTemplate.update(sqlInsert, DEFAULT_TENANT, "keycloak_client_id", "issuer-ui");
            jdbcTemplate.update(sqlInsert, DEFAULT_TENANT, "keycloak_username", "secret");
            jdbcTemplate.update(sqlInsert, DEFAULT_TENANT, "keycloak_password", "secret");

        };
    }
}
