package tech.andersonbrito.authazure.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.andersonbrito.authazure.tenant.infrastructure.TenantContext;
import tech.andersonbrito.authazure.user.CustomOidcUser;
import tech.andersonbrito.authazure.user.core.UserService;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    private final UserService userService;

    public TokenValidationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomOidcUser oidcUser) {
            var optUser = userService.findByUsername(oidcUser.getEmail());
            if (optUser.isEmpty()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autorizado - Cadastrado");
                return;
            }

            var user = optUser.get();
            if (!user.isActive()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autorizado - Inativo");
                return;
            }

            var path = request.getRequestURI();
            if (AdminUrls.toMath(path)) {
                TenantContext.setTenantSchema("admin");
                filterChain.doFilter(request, response);
                return;
            }

            var tenantId = request.getHeader(TENANT_HEADER);
            if (Objects.isNull(tenantId)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autorizado - Tenant não informado");
                return;
            }

            UUID uuid = UUID.fromString(tenantId);
            if (!oidcUser.tenantIsActive(uuid)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autorizado - Tenant inválido");
                return;
            }

            TenantContext.setTenantSchema(oidcUser.getTenantSchema(uuid));
        }

        filterChain.doFilter(request, response);
    }
}
