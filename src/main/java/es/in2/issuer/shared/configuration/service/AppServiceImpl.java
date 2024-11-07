package es.in2.issuer.shared.configuration.service;

import es.in2.issuer.shared.configuration.model.AppConfig;
import es.in2.issuer.shared.configuration.repository.AppConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppServiceImpl {

    private final AppConfigRepository appConfigRepository;

    public String getConfigValue(String key) {
        return appConfigRepository.findByConfigKey(key)
                .map(AppConfig::getConfigValue)
                .orElseThrow(() -> new RuntimeException("Config key not found: " + key));
    }

}