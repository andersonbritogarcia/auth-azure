package tech.andersonbrito.authazure.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import tech.andersonbrito.authazure.tenant.TenantSchema;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public class CustomOidcUser extends DefaultOidcUser {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String userName;
    private final Set<TenantSchema> activeTenants;

    public CustomOidcUser(Collection<? extends GrantedAuthority> authorities,
                          OidcIdToken idToken,
                          OidcUserInfo userInfo,
                          String userName,
                          Set<TenantSchema> activeTenants) {
        super(authorities, idToken, userInfo);
        this.userName = userName;
        this.activeTenants = activeTenants;
    }

    public boolean tenantIsActive(UUID tenantId) {
        return activeTenants.stream()
                            .anyMatch(t -> t.id().equals(tenantId));
    }

    public String getTenantSchema(UUID tenantId){
        var tenant =  activeTenants.stream()
                            .filter(t -> t.id().equals(tenantId))
                            .findFirst();

        return tenant.map(TenantSchema::schema).orElse(null);
    }

    @Override
    public String getName() {
        return this.userName;
    }
}