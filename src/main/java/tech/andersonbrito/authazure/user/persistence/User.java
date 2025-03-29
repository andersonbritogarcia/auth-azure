package tech.andersonbrito.authazure.user.persistence;

import jakarta.persistence.*;
import tech.andersonbrito.authazure.tenant.TenantSchema;
import tech.andersonbrito.authazure.tenant.persistence.Tenant;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_tenant", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tenant_id"))
    private Set<Tenant> tenants;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(nullable = false, name = "role_name")
    private Set<RoleName> roles;

    public User() {
    }

    public User(Long id, String username, UserStatus status, Set<Tenant> tenants, Set<RoleName> roles) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.tenants = tenants;
        this.roles = roles;
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(status);
    }

    public Set<TenantSchema> getActiveTenant() {
        return tenants.stream().filter(Tenant::isActive).map(TenantSchema::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User user)) {
            return false;
        }

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
