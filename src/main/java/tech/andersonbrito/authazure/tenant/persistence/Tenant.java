package tech.andersonbrito.authazure.tenant.persistence;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Tenant {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String schema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantStatus status;

    public Tenant() {
    }

    public Tenant(String name, String schema) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.schema = schema;
        this.status = TenantStatus.ACTIVE;
    }

    public boolean isActive() {
        return TenantStatus.ACTIVE.equals(status);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }
}
