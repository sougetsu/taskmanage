package com.sdmx.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Yan Jingchao
 */
public class EncryptUtils {

    public static String encryptMD5(String source) {
        if (UtilValidate.isEmpty(source)) {
            source = "";
        }
        String result;
        try {
            result = encrypt(source, "MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    public static String encryptSHA(String source) {
        if (UtilValidate.isEmpty(source)) {
            source = "";
        }
        String result;
        try {
            result = encrypt(source, "SHA");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    private static String encrypt(String source, String algorithm)
            throws NoSuchAlgorithmException {
        byte[] resByteArray = encrypt(source.getBytes(), algorithm);
        return toHexString(resByteArray);
    }

    private static byte[] encrypt(byte[] source, String algorithm)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(source);
        return md.digest();
    }

    private static String toHexString(byte[] res) {
        StringBuilder sb = new StringBuilder(res.length << 1);
        for (byte re : res) {
            String digit = Integer.toHexString(0xFF & re);
            if (digit.length() == 1) {
                digit = '0' + digit;
            }
            sb.append(digit);
        }
        return sb.toString().toUpperCase();
    }
}