package com.shieldpointcapital.lrms.domain.converter;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesEncryption {
    //  AES algorithm
     // AES/CBC/PKCS5Padding means:
    // AES       = the encryption algorithm
    // CBC       = Cipher Block Chaining mode — most secure
    // PKCS5Padding = how to handle data that does not fill a full block
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    // IV is always 16 bytes for AES
    private static final int IV_LENGTH = 16;

    // The secret key — injected from your FIELD_ENCRYPTION_KEY env var
    private static String encryptionKey;

    // Spring injects the value from application-dev.properties
    // app.encryption.key=${FIELD_ENCRYPTION_KEY}
    @Value("${app.encryption.key}")
    public void setEncryptionKey(String Key) {
        AesEncryption.encryptionKey = Key;
    }

    // Encrypt

    public static String encrypt(String plainText) {
        if (plainText == null) return null;

        try {
            // Step 1 — prepare the secret key
            // Take first 32 bytes (256 bits) of the key
            byte[] keyBytes = encryptionKey.getBytes("UTF-8");
            byte[] key32 = new byte[32];
            System.arraycopy(keyBytes, 0, key32, 0, Math.min(keyBytes.length,32));
            SecretKeySpec secretKey = new SecretKeySpec(key32, "AES");

            // Step 2 — generate a random IV
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Step 3 - encrypt 
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

            //Step 4 — combine IV + encrypted bytes
            // We store IV alongside the data so we can decrypt later
            byte[] combined = new byte[IV_LENGTH + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
            System.arraycopy(encrypted, 0, combined, IV_LENGTH,encrypted.length);
             // Step 5 — encode to Base64 for safe VARCHAR storage
             return Base64.getEncoder().encodeToString(combined);

            } catch (Exception e) {
                throw new RuntimeException(
                    "Failed to encrypt field value", e);
            }
    }
    public static String decrypt(String encryptedText) {
        if (encryptedText == null) return null;

        try {
            // Step 1 — prepare the secret key
            byte[] keyBytes = encryptionKey.getBytes("UTF-8");
            byte[] key32 = new byte[32];
            System.arraycopy(keyBytes, 0, key32, 0,
                Math.min(keyBytes.length, 32));
            SecretKeySpec secretKey = new SecretKeySpec(key32, "AES");

            // Step 2 — decode from Base64 back to bytes
            byte[] combined = Base64.getDecoder()
                .decode(encryptedText);

            // Step 3 — extract the IV from the first 16 bytes
            byte[] iv = new byte[IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Step 4 — extract the actual encrypted data
            byte[] encrypted = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, IV_LENGTH, encrypted, 0,
                encrypted.length);

            // Step 5 — decrypt
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to decrypt field value", e);
        }
    }
}
