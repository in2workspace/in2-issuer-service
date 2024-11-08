package es.in2.issuer.shared.config;

import es.in2.issuer.shared.repository.CacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class CacheStoreConfig {

    @Bean
    public CacheStore<String> transactionCodeCache() {
        return new CacheStore<>(72, TimeUnit.HOURS);
    }

}
