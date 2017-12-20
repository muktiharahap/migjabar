package com.github.muktiharahap.migjabar.config;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mukti on 10/10/2017.
 */
@Component
public class TimeProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    public Date now() {
        return new Date();
    }
}
