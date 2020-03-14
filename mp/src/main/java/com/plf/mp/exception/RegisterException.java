package com.plf.mp.exception;

/**
 * 注册异常
 * 
 * @author plf
 */
public class RegisterException extends RuntimeException {

    public RegisterException() {}

    public RegisterException(String message) {
        super(message);
    }
}
