package com.github.muktiharahap.migjabar.service;

import java.io.Serializable;

/**
 * @author mukti on 10/10/2017.
 */
public class JwtAuthenticationResponse  implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
