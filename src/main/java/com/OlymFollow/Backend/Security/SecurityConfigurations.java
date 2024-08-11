package com.OlymFollow.Backend.Security;


import com.OlymFollow.Backend.Repositories.UserRepository;
import com.OlymFollow.Backend.Security.filters.AuthenticationFilter;
import com.OlymFollow.Backend.Security.filters.ExceptionHandlingFilter;
import com.OlymFollow.Backend.Security.filters.JWTAuthorizationFilter;
import com.OlymFollow.Backend.Services.JWTokenService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfigurations {

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationFilter authenticationFilter, UserRepository userRepository) throws Exception {
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(AUTH_WHITELIST).permitAll();
                    requests.requestMatchers(HttpMethod.POST, "/user/register").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/OlymFollow/medalhas").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/OlymFollow/medalhas/**").permitAll();
                    requests.anyRequest().authenticated();
                })
                .addFilterBefore(new ExceptionHandlingFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(new JWTokenService(), userRepository), AuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager, JWTokenService jwtTokenService) {
        return new AuthenticationFilter(authenticationManager, jwtTokenService);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                .addSecuritySchemes("bearer-key",
                new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
                .info(new Info()
                .title("OlymFollow API")
                .description("API Rest da aplicação OlymFollow"));
    }
}
