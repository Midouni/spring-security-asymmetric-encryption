package com.midouni.app.auth;

import com.midouni.app.auth.request.AuthenticationRequest;
import com.midouni.app.auth.request.RefreshRequest;
import com.midouni.app.auth.request.RegistrationRequest;
import com.midouni.app.auth.response.AuthenticationResponse;
import com.midouni.app.exception.BusinessException;
import com.midouni.app.exception.ErrorCodes;
import com.midouni.app.role.Role;
import com.midouni.app.role.RoleRepository;
import com.midouni.app.security.JwtService;
import com.midouni.app.user.User;
import com.midouni.app.user.UserMapper;
import com.midouni.app.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationServices{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = (User) auth.getPrincipal();
        final String accessToken = jwtService.generateAccessToken(user.getEmail());
        final String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        final String tokenType="Bearer";
        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).tokenType(tokenType).build();
    }

    @Override
    public void register(RegistrationRequest request) {
        checkEmail(request.getEmail());
        checkPhoneNumber(request.getPhoneNumber());
        checkPassword(request.getPassword(), request.getConfirmPassword());

        Role role = this.roleRepository.findByName("ROLE_USER").orElseThrow(()->new EntityNotFoundException("Role user Not Found"));
        final List<Role> roles = List.of(role);
        User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        role.setUsers(users);
        this.roleRepository.save(role);

    }


    @Override
    public AuthenticationResponse refresh(RefreshRequest request) {

        return null;
    }

    private void checkPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCodes.PASSWORD_MISMATCH);
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        boolean phoneNumberIsExist = this.userRepository.existByPhoneNumber(phoneNumber);
        if(!phoneNumberIsExist) {
            throw new BusinessException(ErrorCodes.PHONE_ALREADY_EXIST);
        }
    }

    private void checkEmail(String email) {
        boolean emailExist = this.userRepository.existsByEmailIgnoreCase(email);
        if (emailExist) {
            throw new BusinessException(ErrorCodes.EMAIL_ALREADY_EXIST)
        }
    }
}
