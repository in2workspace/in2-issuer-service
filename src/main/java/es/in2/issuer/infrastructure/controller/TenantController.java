package es.in2.issuer.infrastructure.controller;

import es.in2.issuer.domain.model.dto.TenantDetails;
import es.in2.issuer.domain.model.dto.TenantRequest;
import es.in2.issuer.domain.model.dto.TenantResponse;
import es.in2.issuer.domain.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issuer/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTenant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody TenantRequest tenantRequest) {
        tenantService.createTenant(authorizationHeader, tenantRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TenantResponse> getTenants(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        TenantResponse tenantResponse = tenantService.getTenants(authorizationHeader);
        return new ResponseEntity<>(tenantResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TenantDetails> getTenant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable String id) {
        TenantDetails tenantDetails = tenantService.getTenant(authorizationHeader, id);
        return new ResponseEntity<>(tenantDetails, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTenant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable String id,
            @RequestBody TenantRequest tenantRequest) {
        tenantService.updateTenant(authorizationHeader, id, tenantRequest);
    }

}
