package es.in2.issuer.security.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import es.in2.issuer.exception.JwtParseException;
import es.in2.issuer.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static es.in2.issuer.exception.ErrorConstantsMessage.JWT_PARSE_ERROR_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final ObjectMapper objectMapper;

    @Override
    public JsonNode parseJwt(String authorizationHeader) {
        try {
            var parsedJwt = SignedJWT.parse(authorizationHeader);
            return objectMapper.readTree(parsedJwt.getPayload().toString());
        } catch (Exception e) {
            log.warn("ERROR: {}", e.getMessage());
            throw new JwtParseException(JWT_PARSE_ERROR_MESSAGE);
        }
    }

}