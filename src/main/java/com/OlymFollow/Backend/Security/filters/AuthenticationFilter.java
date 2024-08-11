package com.OlymFollow.Backend.Security.filters;


import com.OlymFollow.Backend.Entitys.User;
import com.OlymFollow.Backend.Models.AuthData;
import com.OlymFollow.Backend.Services.JWTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTokenService jwtTokenService;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        super.setAuthenticationManager(authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthData data  = new ObjectMapper().readValue(request.getInputStream(), AuthData.class);
            var authentication = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        User user = (User) authResult.getPrincipal();
        var tokenJWT = jwtTokenService.generateToken(user);
        response.addHeader("Authorization", "Bearer " + tokenJWT);
        response.addHeader("UserID", user.getId().toString());
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
