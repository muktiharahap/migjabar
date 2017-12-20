package com.github.muktiharahap.migjabar.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by smg14005 on 9/30/2017.
 */
public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
