package com.example.gp.gp_crud_backend.utilities;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class PasswordUtils {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SALT_LENGTH = 16;

    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    public static String generatePassword() {
        Random random = new Random();
        String password = "";
        for (int i = 0; i < 12; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            password += c;
        }
        return password;
    }

    public static String encryptPassword(String password, String salt) {
        String saltedPassword = password + salt;
        return Base64.getEncoder().encodeToString(saltedPassword.getBytes());
    }
}

