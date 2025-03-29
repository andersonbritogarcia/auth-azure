package tech.andersonbrito.authazure.tenant.web;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.andersonbrito.authazure.tenant.core.TenantService;

import java.util.List;
import java.util.UUID;

@RestController
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/tenants")
    @PreAuthorize("hasRole('ADMIN')")
    TenantResponse createTenant(@RequestBody @Valid TenantRequest request) {
        return new TenantResponse(tenantService.saveTenant(request.toEntity()));
    }

    @GetMapping("/tenants")
    @PreAuthorize("hasRole('ADMIN')")
    List<TenantResponse> getAllTenants() {
        return tenantService.getAllTenants().stream().map(TenantResponse::new).toList();
    }

    @GetMapping("/tenants/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    TenantResponse getTenantById(@PathVariable UUID id) {
        return new TenantResponse(tenantService.getTenantById(id));
    }
}
