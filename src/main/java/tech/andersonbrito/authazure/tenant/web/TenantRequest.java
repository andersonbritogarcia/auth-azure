package tech.andersonbrito.authazure.tenant.web;

import jakarta.validation.constraints.NotBlank;
import tech.andersonbrito.authazure.tenant.persistence.Tenant;

public record TenantRequest(@NotBlank String name, @NotBlank String schema) {

    public Tenant toEntity() {
        return new Tenant(name, schema);
    }
}
