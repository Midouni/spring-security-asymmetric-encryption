package com.midouni.app.auth;

import com.midouni.app.auth.request.AuthenticationRequest;
import com.midouni.app.auth.request.RefreshRequest;
import com.midouni.app.auth.request.RegistrationRequest;
import com.midouni.app.auth.response.AuthenticationResponse;

public interface AuthenticationServices {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    void register(RegistrationRequest registrationRequest);

    AuthenticationResponse refresh(RefreshRequest refreshRequest);
}
