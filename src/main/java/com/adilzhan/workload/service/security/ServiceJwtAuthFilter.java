package com.adilzhan.workload.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;

@Component
public class ServiceJwtAuthFilter extends OncePerRequestFilter {

    private final Key key;
    private final String expectedIssuer;
    private final String expectedAudience;

    public ServiceJwtAuthFilter(
            @Value("${service-auth.shared-secret}") String secret,
            @Value("${service-auth.issuer}") String issuer,
            @Value("${service-auth.audience}") String audience
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expectedIssuer = issuer;
        this.expectedAudience = audience;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/api/v1/workload");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = auth.substring("Bearer ".length()).trim();
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();

            if (!expectedIssuer.equals(claims.getIssuer())
                    || !expectedAudience.equals(claims.getAudience())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Authentication authentication = new ServiceAuthenticationToken(
                    claims.get("svc", String.class)
            );
            org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } finally {
            org.springframework.security.core.context.SecurityContextHolder.clearContext();
        }
    }

    static class ServiceAuthenticationToken extends AbstractAuthenticationToken {
        private final String principal;

        ServiceAuthenticationToken(String principal) {
            super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_SERVICE")));
            this.principal = principal;
            super.setAuthenticated(true);
        }

        @Override
        public Object getCredentials() { return ""; }

        @Override
        public Object getPrincipal() { return principal; }
    }
}
