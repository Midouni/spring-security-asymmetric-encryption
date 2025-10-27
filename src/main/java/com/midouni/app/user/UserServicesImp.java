package com.midouni.app.user;

import com.midouni.app.exception.BusinessException;
import com.midouni.app.exception.ErrorCodes;
import com.midouni.app.user.request.ChangePasswordRequest;
import com.midouni.app.user.request.ProfileUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServicesImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;



    @Override
    public void updateProfileInfo(ProfileUpdateRequest request, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, userId));
        this.userMapper.mergerUserInfo(user,request);
        this.userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {

    }

    @Override
    public void deactivateAccount(String userId) {

    }

    @Override
    public void reactivateAccount(String userId) {

    }

    @Override
    public void deleteAccount(String userId) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
