package tech.andersonbrito.authazure.tenant.core;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.andersonbrito.authazure.tenant.persistence.Tenant;
import tech.andersonbrito.authazure.tenant.persistence.TenantRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant getTenantById(UUID id) {
        return tenantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tenant not found"));
    }
}
