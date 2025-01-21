package com.hospital.security.config;

import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import javax.crypto.Cipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    
    @Autowired
    private RSAPrivateKey privateKey;
    
    public String decryptPassword(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (Exception e) {
            logger.error("Şifre çözme hatası", e);
            return null;
        }
    }
    
    public boolean authenticate(String username, String encryptedPassword) {
        try {
            String decryptedPassword = decryptPassword(encryptedPassword);
            return decryptedPassword != null;
        } catch (Exception e) {
            logger.error("Kimlik doğrulama hatası", e);
            return false;
        }
    }
}
