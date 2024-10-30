package es.in2.issuer.security.service;

public interface PolicyAuthorizationService {
        void authorize(String authorizationHeader, String schema);
}
