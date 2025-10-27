package com.midouni.app.exception;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCodes errorCode;
    private final Object[] args;

    public BusinessException(ErrorCodes errorCode, Object... args) {
        super(getFormatterMessage(errorCode,args));
        this.errorCode = errorCode;
        this.args = args;
    }

    private static String getFormatterMessage(ErrorCodes errorCode, Object[] args) {
        if (args == null || args.length == 0) {
            return String.format(errorCode.getDefaultMessage(), args);
        }
        return String.format(errorCode.getDefaultMessage(), (Object) null);
    }
}
