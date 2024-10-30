package es.in2.issuer.security.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface JwtService {
    JsonNode parseJwt(String authorizationHeader);
}
