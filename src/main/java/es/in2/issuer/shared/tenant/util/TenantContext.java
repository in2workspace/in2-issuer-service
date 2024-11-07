package es.in2.issuer.shared.tenant.util;

public class TenantContext {

    private TenantContext() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }

}
