package tech.andersonbrito.authazure.user.core;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import tech.andersonbrito.authazure.user.CustomOidcUser;
import tech.andersonbrito.authazure.user.persistence.User;
import tech.andersonbrito.authazure.user.persistence.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public OAuth2UserService<OidcUserRequest, OidcUser> generateCustomOidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return userRequest -> {
            var oidcUser = delegate.loadUser(userRequest);

            var optUser = repository.findByUsername(oidcUser.getEmail());
            if (optUser.isEmpty()) {
                return oidcUser;
            }

            var user = optUser.get();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());

            user.getRoles().forEach(role -> {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
                mappedAuthorities.add(authority);
            });

            var activeTenants = user.getActiveTenant();
            return new CustomOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), oidcUser.getName(), activeTenants);
        };
    }
}
