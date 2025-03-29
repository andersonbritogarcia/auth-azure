package tech.andersonbrito.authazure.tenant.web;

import tech.andersonbrito.authazure.tenant.persistence.Tenant;
import tech.andersonbrito.authazure.tenant.persistence.TenantStatus;

import java.util.UUID;

public record TenantResponse(UUID id, String name, String schema, TenantStatus status) {

    public TenantResponse(Tenant tenant) {
        this(tenant.getId(), tenant.getName(), tenant.getSchema(), tenant.getStatus());
    }
}
