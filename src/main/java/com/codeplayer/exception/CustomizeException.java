package com.codeplayer.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

}
