package az.ingress.configuration;

import az.ingress.service.concurate.AuthServiceHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthServiceHandler authServiceHandler;



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
