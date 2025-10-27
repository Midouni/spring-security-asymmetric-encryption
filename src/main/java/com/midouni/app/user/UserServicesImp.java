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

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServicesImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void updateProfileInfo(ProfileUpdateRequest request, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, userId));
        this.userMapper.mergerUserInfo(user,request);
        this.userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {
        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            throw new BusinessException(ErrorCodes.CHANGE_PASSWORD_MISMATCH);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, userId));
        if(!passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCodes.INVALID_CURRENT_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        this.userRepository.save(user);
    }

    @Override
    public void deactivateAccount(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, userId));
        if(!user.isEnabled()){
            throw new BusinessException(ErrorCodes.ACCOUNT_ALREADY_DEACTIVATED);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void reactivateAccount(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, userId));
        if(user.isEnabled()){
            throw new BusinessException(ErrorCodes.ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(String userId) {
        // TODO -- After rest of the entities, schedule a profile for deletion, the schedule job will pick the profile and delete everything
    }
}
