package tech.andersonbrito.authazure.tenant.infrastructure;

import org.springframework.util.Assert;

public class TenantContext {

    private static final ThreadLocal<String> tenantSchema = new ThreadLocal<>();

    public static void setTenantSchema(String schema) {
        Assert.hasText(schema, "tenant cannot be empty");
        tenantSchema.set(schema);
    }

    public static String getTenantSchema() {
        return tenantSchema.get();
    }

    public static void clear() {
        tenantSchema.remove();
    }
}
