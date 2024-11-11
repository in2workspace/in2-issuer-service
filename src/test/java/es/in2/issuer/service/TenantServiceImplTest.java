package es.in2.issuer.service;

import es.in2.issuer.domain.exception.TenantAlreadyExistException;
import es.in2.issuer.domain.model.dto.TenantDetails;
import es.in2.issuer.domain.model.dto.TenantRequest;
import es.in2.issuer.domain.model.dto.TenantResponse;
import es.in2.issuer.domain.model.entity.Tenant;
import es.in2.issuer.infrastructure.repository.TenantRepository;
import es.in2.issuer.domain.service.impl.TenantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static es.in2.issuer.domain.exception.ErrorConstantsMessage.TENANT_NOT_FOUND_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TenantServiceImplTest {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private TenantServiceImpl tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTenant_Success() {
        TenantRequest tenantRequest = new TenantRequest("Test Tenant", "test.com");
        when(tenantRepository.findByTenantDomain(tenantRequest.tenantDomain())).thenReturn(Optional.empty());

        tenantService.createTenant("Bearer token", tenantRequest);

        verify(tenantRepository, times(1)).save(any(Tenant.class));
    }

    @Test
    void testCreateTenant_AlreadyExists() {
        TenantRequest tenantRequest = new TenantRequest("Test Tenant", "test.com");
        Tenant existingTenant = new Tenant(UUID.randomUUID(), "Test Tenant", "test.com");
        when(tenantRepository.findByTenantDomain(tenantRequest.tenantDomain())).thenReturn(Optional.of(existingTenant));

        assertThrows(TenantAlreadyExistException.class,
                () -> tenantService.createTenant("Bearer token", tenantRequest),
                "Tenant already exists");
    }

    @Test
    void testUpdateTenant_Success() {
        UUID tenantId = UUID.randomUUID();
        TenantRequest tenantRequest = new TenantRequest("Updated Tenant", "updated.com");
        Tenant existingTenant = new Tenant(tenantId, "Old Tenant", "old.com");

        when(tenantRepository.findById(String.valueOf(tenantId))).thenReturn(Optional.of(existingTenant));

        tenantService.updateTenant("Bearer token", String.valueOf(tenantId), tenantRequest);

        verify(tenantRepository, times(1)).save(existingTenant);
        assertEquals("Updated Tenant", existingTenant.getTenantName());
        assertEquals("updated.com", existingTenant.getTenantDomain());
    }

    @Test
    void testUpdateTenant_NotFound() {
        String tenantId = UUID.randomUUID().toString();
        TenantRequest tenantRequest = new TenantRequest("Updated Tenant", "updated.com");

        when(tenantRepository.findById(tenantId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> tenantService.updateTenant("Bearer token", tenantId, tenantRequest));
        assertEquals(TENANT_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testGetTenants() {
        Tenant tenant1 = new Tenant(UUID.randomUUID(), "Tenant One", "tenant1.com");
        Tenant tenant2 = new Tenant(UUID.randomUUID(), "Tenant Two", "tenant2.com");

        when(tenantRepository.findAll()).thenReturn(List.of(tenant1, tenant2));

        TenantResponse tenantResponse = tenantService.getTenants("Bearer token");

        assertNotNull(tenantResponse);
        assertEquals(2, tenantResponse.tenantDetailsList().size());
        assertEquals("Tenant One", tenantResponse.tenantDetailsList().get(0).tenantName());
        assertEquals("tenant2.com", tenantResponse.tenantDetailsList().get(1).tenantDomain());
    }

    @Test
    void testGetTenant_Success() {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant(tenantId, "Test Tenant", "test.com");

        when(tenantRepository.findById(String.valueOf(tenantId))).thenReturn(Optional.of(tenant));

        TenantDetails tenantDetails = tenantService.getTenant("Bearer token", String.valueOf(tenantId));

        assertNotNull(tenantDetails);
        assertEquals("Test Tenant", tenantDetails.tenantName());
        assertEquals("test.com", tenantDetails.tenantDomain());
    }

    @Test
    void testGetTenant_NotFound() {
        String tenantId = UUID.randomUUID().toString();
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> tenantService.getTenant("Bearer token", tenantId));
        assertEquals(TENANT_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
    }

}
