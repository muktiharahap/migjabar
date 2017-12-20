package com.github.muktiharahap.migjabar.service.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author mukti on 10/13/2017.
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }
}
