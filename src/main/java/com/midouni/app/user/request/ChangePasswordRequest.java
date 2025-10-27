package com.midouni.app.user.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePassword {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
