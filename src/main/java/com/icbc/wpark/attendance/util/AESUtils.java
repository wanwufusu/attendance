package com.icbc.wpark.attendance.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

public class AESUtils {

    private static String KEY = "3nwrc70tb9vae0z2";

    private static String IV = "A-16-Byte-String";

    public static SecretKeySpec getKey() throws Exception {
        KeyGenerator aes = KeyGenerator.getInstance("AES");
        aes.init(256, new SecureRandom(KEY.getBytes()));
        Security.addProvider(new BouncyCastleProvider());
        return new SecretKeySpec(KEY.getBytes(),"AES");
    }

    public static String AESEncode(String content) throws Exception {
        SecretKeySpec key = getKey();
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        ci.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));
        byte[] encodeBytes = ci.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(encodeBytes);
    }

    public static String AESDecode(String content) throws Exception {
        SecretKeySpec key = getKey();
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        ci.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));
        byte[] contentBytes = Base64Utils.decodeFromString(content);
        byte[] decodeBytes = ci.doFinal(contentBytes);
        return new String(decodeBytes, StandardCharsets.UTF_8);
    }
}
