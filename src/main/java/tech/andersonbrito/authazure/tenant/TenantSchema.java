package tech.andersonbrito.authazure.tenant;

import tech.andersonbrito.authazure.tenant.persistence.Tenant;

import java.util.UUID;

public record TenantSchema(UUID id, String schema) {

    public TenantSchema(Tenant tenant) {
        this(tenant.getId(), tenant.getSchema());
    }
}
