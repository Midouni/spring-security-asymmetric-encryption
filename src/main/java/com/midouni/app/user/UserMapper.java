package com.midouni.app.user;



import com.midouni.app.auth.request.RegistrationRequest;
import com.midouni.app.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public void mergerUserInfo(User user, ProfileUpdateRequest request) {
        user.setFirstName(StringUtils.isNotBlank(request.getFirstName()) ? request.getFirstName() : user.getFirstName());
        user.setLastName(StringUtils.isNotBlank(request.getLastName()) ? request.getLastName() : user.getLastName());
        user.setBirthday(request.getBirthday() != null ? request.getBirthday() : user.getBirthday());
    }

    public User toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .emailVerified(false)
                .phoneVerified(false)
                .build();
    }
}
