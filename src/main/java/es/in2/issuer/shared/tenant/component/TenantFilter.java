package es.in2.issuer.shared.tenant.component;

import es.in2.issuer.shared.tenant.util.TenantContext;
import es.in2.issuer.shared.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TenantFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract tenant ID from request
        String tenantId = jwtService
                .parseJwt(request.getHeader(HttpHeaders.AUTHORIZATION))
                // todo: verify the claim used to set the organization
                .get("organization")
                .asText();
        // Set tenant to context
        TenantContext.setTenantId(tenantId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

}
