package es.in2.issuer.service.impl;

import es.in2.issuer.exception.TenantAlreadyExistException;
import es.in2.issuer.model.dto.TenantDetails;
import es.in2.issuer.model.dto.TenantRequest;
import es.in2.issuer.model.dto.TenantResponse;
import es.in2.issuer.model.entity.Tenant;
import es.in2.issuer.repository.TenantRepository;
import es.in2.issuer.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static es.in2.issuer.exception.ErrorConstantsMessage.TENANT_ALREADY_EXISTS_ERROR_MESSAGE;
import static es.in2.issuer.exception.ErrorConstantsMessage.TENANT_NOT_FOUND_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public void createTenant(String authorizationHeader, TenantRequest tenantRequest) {
        Optional<Tenant> tenantFound = getTenantByDomain(tenantRequest.tenantDomain());
        if (tenantFound.isPresent()) {
            throw new TenantAlreadyExistException(TENANT_ALREADY_EXISTS_ERROR_MESSAGE);
        }
        Tenant tenant = Tenant.builder()
                .tenantId(UUID.randomUUID())
                .tenantName(tenantRequest.tenantName())
                .tenantDomain(tenantRequest.tenantDomain())
                .build();
        tenantRepository.save(tenant);
    }

    @Override
    public void updateTenant(String authorizationHeader, String tenantId, TenantRequest tenantRequest) {
        Optional<Tenant> tenantOptional = tenantRepository.findById(tenantId);
        if (tenantOptional.isPresent()) {
            Tenant tenant = tenantOptional.get();
            tenant.setTenantName(tenantRequest.tenantName());
            tenant.setTenantDomain(tenantRequest.tenantDomain());
            tenantRepository.save(tenant);
        } else {
            throw new NoSuchElementException(TENANT_NOT_FOUND_ERROR_MESSAGE);
        }
    }

    @Override
    public TenantResponse getTenants(String authorizationHeader) {
        // Get all tenants
        Iterable<Tenant> tenants = getAllTenants();

        // Convert Iterable to List
        List<TenantDetails> tenantDetailsList = StreamSupport.stream(tenants.spliterator(), false)
                .map(this::mapTenantToTenantDetails)
                .toList();

        // Return TenantResponse
        return TenantResponse.builder()
                .tenantDetailsList(tenantDetailsList)
                .build();
    }

    @Override
    public TenantDetails getTenant(String authorizationHeader, String tenantId) {
        return getTenantById(tenantId)
                .map(this::mapTenantToTenantDetails)
                .orElseThrow(() -> new NoSuchElementException(TENANT_NOT_FOUND_ERROR_MESSAGE));
    }

    private Optional<Tenant> getTenantByDomain(String tenantDomain) {
        return tenantRepository.findByTenantDomain(tenantDomain);
    }

    private Optional<Tenant> getTenantById(String tenantId) {
        return tenantRepository.findById(tenantId);
    }

    private Iterable<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    private TenantDetails mapTenantToTenantDetails(Tenant tenant) {
        return TenantDetails.builder()
                .tenantId(String.valueOf(tenant.getTenantId()))
                .tenantName(tenant.getTenantName())
                .tenantDomain(tenant.getTenantDomain())
                .build();
    }

}
