package com.OlymFollow.Backend.Controllers;


import com.OlymFollow.Backend.Dtos.UserRegisterDTO;
import com.OlymFollow.Backend.Models.AuthGoogle;
import com.OlymFollow.Backend.Security.infra.NotFoundException;
import com.OlymFollow.Backend.Services.GoogleService;
import com.OlymFollow.Backend.Services.JWTokenService;
import com.OlymFollow.Backend.Services.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
@RequestMapping("/authenticate")
public class AuthController {

    final private GoogleService googleService;
    final private UserService userService;
    final private JWTokenService jwtTokenService;

    @Autowired
    public AuthController(GoogleService googleService, UserService userService, JWTokenService jwtTokenService){
        this.googleService = googleService;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }


    @PostMapping(value = "/google")
    @Operation(summary = "Faz login com google")
    public ResponseEntity<Object> login(@RequestBody AuthGoogle data) throws GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = googleService.verifyToken(data.accessToken());
        var user = new UserRegisterDTO(payload);
        Long userID;
        try{
            var userExists = userService.getUserByUsername(user.getUsername());
            userID = userExists.getId();
        }catch (NotFoundException e){
            var newUser = userService.addUser(user);
            userID = newUser.getId();
        }
        var token = jwtTokenService.generateToken(user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("userID", userID.toString());
        return ResponseEntity.ok().headers(headers).build();
    }


}
