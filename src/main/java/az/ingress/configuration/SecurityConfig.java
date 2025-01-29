package az.ingress.configuration;


import az.ingress.service.concurate.AuthServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final AuthServiceHandler authServiceHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF deaktiv
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/v1/auth/**", "/v1/users/sign-in","/swagger-ui/**","swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll() // Açıq endpointlər
                        .anyRequest().authenticated() // Qalan bütün sorğular doğrulama tələb edir
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sessiya idarəetməsi deaktiv

        return http.build();
    }
}


@Component
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthServiceHandler authServiceHandler;

    public JwtAuthenticationFilter(AuthServiceHandler authServiceHandler) {
        this.authServiceHandler = authServiceHandler;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        String authorizationHeader = request.getHeader("Authorization");

        String requestURI = request.getRequestURI();


        if (requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars")) {
            filterChain.doFilter(request, response); // Swagger resurslarına filtrləmə tətbiq etmə
            return;
        }


        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Tokeni çıxar və yoxla
        String token = authorizationHeader.substring(7);
        if (!authServiceHandler.verifyToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Token doğrulandısa, Security Context-ə istifadəçi məlumatlarını əlavə et
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(token, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Sorğunu növbəti filterə göndər
        filterChain.doFilter(request, response);
    }
}


