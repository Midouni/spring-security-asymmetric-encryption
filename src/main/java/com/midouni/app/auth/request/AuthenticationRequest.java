package com.midouni.app.auth.request;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @Email
    private String email;
    private String password;
}
