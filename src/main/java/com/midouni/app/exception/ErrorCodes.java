package com.midouni.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {

    USER_NOT_FOUND("USER_NOT_FOUND","User with id: %s Not Found", HttpStatus.NOT_FOUND),;

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
