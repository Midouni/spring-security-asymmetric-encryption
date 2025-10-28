package com.midouni.app.user;

import com.midouni.app.user.request.ChangePasswordRequest;
import com.midouni.app.user.request.ProfileUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "user api")
public class UserController {
    private final UserServicesImp userService;

    @PatchMapping("/update-info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfileInfo(
            @RequestBody @Valid ProfileUpdateRequest profileUpdateRequest,
            Authentication authentication
    ) {
        this.userService.updateProfileInfo(profileUpdateRequest, ((User) authentication.getPrincipal()).getId());
    }

    @PatchMapping("/update-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            @RequestBody @Valid
            ChangePasswordRequest changePasswordRequest,
            Authentication authentication
    ){
        this.userService.changePassword(changePasswordRequest, ((User) authentication.getPrincipal()).getId());
    }

    @PatchMapping("/deactivate-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateAccount(
            Authentication authentication
    ){
        this.userService.deactivateAccount(((User) authentication.getPrincipal()).getId());
    }

    @PatchMapping("/reactivate-account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateAccount(
            Authentication authentication
    ){
        this.userService.reactivateAccount(((User) authentication.getPrincipal()).getId());
    }

    @DeleteMapping("delete-account")
    public void deleteAccount(){
        // TODO --
    }
}
