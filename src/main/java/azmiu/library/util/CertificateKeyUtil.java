package azmiu.library.util;

import azmiu.library.exception.AuthException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64; // <-- Bunu əlavə et

import static azmiu.library.exception.ErrorMessage.USER_UNAUTHORIZED;
import static azmiu.library.model.constants.AuthConstants.KEY_SIZE;
import static azmiu.library.model.constants.AuthConstants.RSA;

@Slf4j
public enum CertificateKeyUtil {
    CERTIFICATE_KEY_UTIL;

    public KeyPair generateKeyPair() {
        try {
            var keyPairGen = KeyPairGenerator.getInstance(RSA);
            keyPairGen.initialize(KEY_SIZE);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error("ActionLog.generateKeyPair.error ", e);
            throw new AuthException(USER_UNAUTHORIZED.getMessage(), 401);
        }
    }

    @SneakyThrows
    public PublicKey getPublicKey(String publicKey) {
        var keyFactory = KeyFactory.getInstance(RSA);

        var keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        return keyFactory.generatePublic(keySpec);
    }
}
