package com.OlymFollow.Backend.Security.filters;

import com.OlymFollow.Backend.Repositories.UserRepository;
import com.OlymFollow.Backend.Security.infra.NotFoundException;
import com.OlymFollow.Backend.Services.JWTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/**")
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;
    private final JWTokenService jwtTokenService;

    @Autowired
    public JWTAuthorizationFilter(JWTokenService jwtTokenService, UserRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = getToken(request);
        if (tokenJWT != null) {
            var username = jwtTokenService.verifyToken(tokenJWT);
            var user = userRepository.findByUsername(username);
            if (user.isEmpty()) throw new NotFoundException();
            var authentication = new UsernamePasswordAuthenticationToken(username, null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    public String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.replace("Bearer ", "");
    }

}
