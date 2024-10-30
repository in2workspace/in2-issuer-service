package es.in2.issuer.shared.security.service;

public interface PolicyAuthorizationService {
        void authorize(String authorizationHeader, String schema);
}
