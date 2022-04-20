package com.example.demo.framework.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author JiakunXu
 */
public class EncryptUtil {

    public static String encryptMd5(String data) throws IOException {
        byte[] bytes = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }

        return byte2hex(bytes);
    }

    public static String encryptSha(String data) throws IOException {
        byte[] bytes = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }

        return byte2hex(bytes);
    }

    public static String encryptHmac(String data, String secret) throws IOException {
        byte[] bytes = null;

        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }

        return byte2hex(bytes);
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }

        return sign.toString();
    }

}
