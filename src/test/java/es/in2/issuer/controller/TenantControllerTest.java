package es.in2.issuer.controller;

import es.in2.issuer.model.dto.TenantDetails;
import es.in2.issuer.model.dto.TenantRequest;
import es.in2.issuer.model.dto.TenantResponse;
import es.in2.issuer.service.TenantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TenantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private TenantController tenantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tenantController).build();
    }

    @Test
    void createTenant_ShouldReturnStatusCreated() throws Exception {
        doNothing().when(tenantService).createTenant(any(String.class), any(TenantRequest.class));

        mockMvc.perform(post("/issuer/v1/tenants")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Tenant\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getTenants_ShouldReturnTenantResponse() throws Exception {
        TenantResponse tenantResponse = TenantResponse.builder().build();
        when(tenantService.getTenants(any(String.class))).thenReturn(tenantResponse);

        mockMvc.perform(get("/issuer/v1/tenants")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getTenant_ShouldReturnTenantDetails() throws Exception {
        TenantDetails tenantDetails = TenantDetails.builder().build();
        when(tenantService.getTenant(any(String.class), eq("1"))).thenReturn(tenantDetails);

        mockMvc.perform(get("/issuer/v1/tenants/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateTenant_ShouldReturnNoContent() throws Exception {
        doNothing().when(tenantService).updateTenant(any(String.class), eq("1"), any(TenantRequest.class));

        mockMvc.perform(patch("/issuer/v1/tenants/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Tenant\"}"))
                .andExpect(status().isNoContent());
    }

}
