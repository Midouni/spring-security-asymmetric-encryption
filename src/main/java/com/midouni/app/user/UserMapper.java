package com.midouni.app.user;



import com.midouni.app.user.request.ProfileUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public void mergerUserInfo(User user, ProfileUpdateRequest request) {
        user.setFirstName(StringUtils.isNotBlank(request.getFirstName()) ? request.getFirstName() : user.getFirstName());
        user.setLastName(StringUtils.isNotBlank(request.getLastName()) ? request.getLastName() : user.getLastName());
        user.setBirthday(request.getBirthday() != null ? request.getBirthday() : user.getBirthday());
    }
}
