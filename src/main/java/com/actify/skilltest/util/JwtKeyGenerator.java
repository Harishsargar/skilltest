package com.actify.skilltest.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        // Generate a secure Base64-encoded secret key
        String key = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
        
        // Print the generated key
        System.out.println("Generated Secret Key: " + key);
    }
}