package es.in2.issuer.oidc4vci.controller;

import es.in2.issuer.oidc4vci.model.dto.IssuerMetadata;
import es.in2.issuer.oidc4vci.service.IssuerMetadataService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known/openid-credential-issuer")
@RequiredArgsConstructor
public class IssuerMetadataController {
    private final IssuerMetadataService issuerMetadataService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public IssuerMetadata getIssuerMetadata(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_LANGUAGE, "en");
        return issuerMetadataService.getIssuerMetadata();
    }
}
