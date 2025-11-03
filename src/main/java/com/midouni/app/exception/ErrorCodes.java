package com.midouni.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {

    USER_NOT_FOUND("USER_NOT_FOUND","User with id: %s Not Found", HttpStatus.NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH","Change Password Mismatch", HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD","Invalid Current Password" , HttpStatus.BAD_REQUEST ),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED","Account Already Activated" , HttpStatus.BAD_REQUEST ),
    ACCOUNT_ALREADY_ACTIVATED("ACCOUNT_ALREADY_ACTIVATED","Account Already Activated" , HttpStatus.BAD_REQUEST ),
    EMAIL_ALREADY_EXIST("EMAIL_ALREADY_EXIST","Email Already Exist" , HttpStatus.BAD_REQUEST ),
    PHONE_ALREADY_EXIST("PHONE_ALREADY_EXIST" ,"Phone Already Exist" , HttpStatus.BAD_REQUEST ),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH","Password Mismatch" , HttpStatus.BAD_REQUEST ),;

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCodes(
            String code,
            String defaultMessage,
            HttpStatus httpStatus
    ){
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }

}
