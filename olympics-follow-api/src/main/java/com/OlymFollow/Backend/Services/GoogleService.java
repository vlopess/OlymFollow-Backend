package com.OlymFollow.Backend.Services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleService {

    @Value("${google.clientId}")
    private String OIDC_CLIENT_ID;

    private final GoogleIdTokenVerifier verifier;

    public GoogleService()  {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory()).setAudience(Collections.singletonList(OIDC_CLIENT_ID)).build();
    }


    public GoogleIdToken.Payload verifyToken(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken = GoogleIdToken.parse(verifier.getJsonFactory(), token);
        verifier.verify(idToken);
        return idToken.getPayload();
    }
}
