package com.midouni.app.security;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
    private KeyUtils() {}

    public static PrivateKey loadPrivateKey(final String pemPath) throws Exception {
        final String key = readKeyFromResource(pemPath)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("","-----END PRIVATE KEY-----")
                .replace("\\s+","");
        final byte[] decoded = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(final String pemPath) throws Exception {
        final String key = readKeyFromResource(pemPath)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("","-----END PUBLIC KEY-----")
                .replace("\\s+","");
        final byte[] decoded = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    public static String readKeyFromResource(final String pemPath) throws Exception{
        try (final InputStream in = KeyUtils.class.getResourceAsStream(pemPath)) {
            if (in == null) {
                throw new IllegalArgumentException("Resource not found: " + pemPath);
            }
            return new String((in.readAllBytes()));
        }
    }
}
