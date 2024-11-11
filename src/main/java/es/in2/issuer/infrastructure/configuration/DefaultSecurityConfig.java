package es.in2.issuer.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class DefaultSecurityConfig {

    private final IssuerUiConfig issuerUiConfig;

    public DefaultSecurityConfig(IssuerUiConfig issuerUiConfig) {
        this.issuerUiConfig = issuerUiConfig;
    }

    // Session management
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        // No session management
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer ->
                        // JWT validation
                        oauth2ResourceServer.jwt(withDefaults())
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Origins that are allowed to access the resource
        corsConfig.setAllowedOrigins(List.of(issuerUiConfig.getIssuerUiUrl()));
        corsConfig.setMaxAge(8000L);
        corsConfig.setAllowedMethods(List.of(HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(), HttpMethod.POST.name(),
                HttpMethod.GET.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name()));
        corsConfig.addAllowedHeader("*");
        corsConfig.addExposedHeader("*");
        corsConfig.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply the configuration to all paths
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}
