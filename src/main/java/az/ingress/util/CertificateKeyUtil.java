package az.ingress.util;


import az.ingress.exception.AuthException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import static az.ingress.exception.ErrorMessage.USER_UNAUTHORIZED;
import static az.ingress.model.constants.AuthConstants.KEY_SIZE;
import static az.ingress.model.constants.AuthConstants.RSA;
import static org.springframework.util.Base64Utils.decodeFromString;

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
        var keySpec = new X509EncodedKeySpec(decodeFromString(publicKey));
        return keyFactory.generatePublic(keySpec);
    }
}