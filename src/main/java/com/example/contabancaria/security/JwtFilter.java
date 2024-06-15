package com.example.contabancaria.security;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");

            try {
                String userId = Jwts.parser()
                        .setSigningKey("5945a15423121ecc3542f9a7e30028dda8a17a8ab3cd8ef56ffa5f9cf43e3500")
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                if (userId != null) {
                    Authentication auth = new JwtAuthentication(userId);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
