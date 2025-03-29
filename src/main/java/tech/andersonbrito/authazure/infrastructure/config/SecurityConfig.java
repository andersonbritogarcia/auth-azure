package tech.andersonbrito.authazure.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.andersonbrito.authazure.infrastructure.filter.TenantCleanupFilter;
import tech.andersonbrito.authazure.infrastructure.filter.TokenValidationFilter;
import tech.andersonbrito.authazure.user.core.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   TokenValidationFilter tokenValidationFilter,
                                                   TenantCleanupFilter tenantCleanupFilter) throws Exception {

        http.authorizeHttpRequests(authorize ->
                                           authorize.requestMatchers("/v3/api-docs").permitAll()
                                                    .requestMatchers("/v3/api-docs/**").permitAll()
                                                    .requestMatchers("/swagger-ui/**").permitAll()
                                                    .requestMatchers("/swagger-resources/**").permitAll()
                                                    .requestMatchers("/webjars/**").permitAll()
                                                    .requestMatchers(("/token_details")).permitAll()
                                                    .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2Login(outh2 ->
                                 outh2.userInfoEndpoint(userInfo -> userInfo.oidcUserService(userService.generateCustomOidcUserService())));

        http.addFilterBefore(tokenValidationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(tenantCleanupFilter, TokenValidationFilter.class);

        return http.build();
    }
}