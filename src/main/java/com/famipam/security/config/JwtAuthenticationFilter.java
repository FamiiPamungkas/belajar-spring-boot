package com.famipam.security.config;

import com.famipam.security.entity.User;
import com.famipam.security.exception.UserDisabledException;
import com.famipam.security.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("### Filter");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwt = authHeader.substring(7);
            username = jwtService.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = this.userService.findByUsername(username);
                System.out.println("-> user Enabled ? " + userDetails.isEnabled());
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    if (!userDetails.isEnabled()) throw new UserDisabledException("User is Disabled");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (SignatureException | ExpiredJwtException | UserDisabledException e) {
            onError(HttpStatus.UNAUTHORIZED, response, e);
        } catch (Exception e) {
            e.printStackTrace();
            onError(response, e);
        }
    }

    private void onError(HttpServletResponse response, Exception e) throws IOException {
        onError(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
    }

    private void onError(HttpStatus httpStatus, HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.getWriter().write("{ \"status\": \"" + httpStatus.value() + "\", \"message\": \"" + e.getMessage() + "\" }");
    }
}
