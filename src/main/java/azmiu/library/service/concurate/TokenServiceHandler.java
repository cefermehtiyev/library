package azmiu.library.service.concurate;


import azmiu.library.configuration.property.TokenExpirationProperties;
import azmiu.library.dao.entity.SessionTokenEntity;
import azmiu.library.dao.repository.SessionTokenRepository;
import azmiu.library.exception.AuthException;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.cache.AuthCacheData;
import org.springframework.stereotype.Service;


import azmiu.library.model.jwt.AccessTokenClaimsSet;
import azmiu.library.model.jwt.RefreshTokenClaimsSet;
import azmiu.library.model.response.AuthPayloadDto;
import azmiu.library.model.response.AuthResponse;
import azmiu.library.service.abstraction.TokenService;
import azmiu.library.util.CacheProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import static azmiu.library.exception.ErrorMessage.REFRESH_TOKEN_COUNT_EXPIRED;
import static azmiu.library.exception.ErrorMessage.TOKEN_EXPIRED;
import static azmiu.library.exception.ErrorMessage.UNEXPECTED_ERROR;
import static azmiu.library.exception.ErrorMessage.USER_UNAUTHORIZED;
import static azmiu.library.mapper.TokenMapper.TOKEN_MAPPER;
import static azmiu.library.model.constants.CacheConstants.CACHE_EXPIRE_SECONDS;
import static azmiu.library.util.CertificateKeyUtil.CERTIFICATE_KEY_UTIL;
import static azmiu.library.util.JwtUtil.JWT_UTIL;
import static jodd.util.Base64.encodeToString;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceHandler implements TokenService {
    private final TokenExpirationProperties tokenExpirationProperties;
    private final CacheProvider cacheProvider;
    private final SessionTokenRepository sessionTokenRepository;

    public AuthResponse prepareToken(String userId) {
        final var refreshTokenExpirationCount = 50;
        return generateToken(userId, refreshTokenExpirationCount);
    }

    public String getUserIdFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // 'Bearer ' hissəsini silirik
            }
            var jwtClaimsSet = JWT_UTIL.parseSignedJwt(token);
            var userId = jwtClaimsSet.getJWTClaimsSet().getClaim("userId").toString();
            log.info("Token Parse {}",userId);
            return userId;
        } catch (Exception e) {
            log.error("ActionLog.getUserIdFromAccessToken.error ", e);
            throw new AuthException(USER_UNAUTHORIZED.getMessage(), 401);
        }
    }


    public AuthResponse refreshToken(String refreshToken) {
        var refreshTokenClaimsSet = JWT_UTIL.getClaimsFromToken(refreshToken, RefreshTokenClaimsSet.class);
        var refreshTokenExpirationCount = refreshTokenClaimsSet.getCount() - 1;
        var userId = refreshTokenClaimsSet.getUserId();

        try {
            var authCacheData = fetchFromCache(userId);

            if (authCacheData == null) throw new AuthException(USER_UNAUTHORIZED.getMessage(), 401);

            var publicKey = CERTIFICATE_KEY_UTIL.getPublicKey(authCacheData.getPublicKey());

            JWT_UTIL.verifySignature(refreshToken, publicKey);

            verifyRefreshToken(refreshTokenClaimsSet);

            return generateToken(userId, refreshTokenExpirationCount);
        } catch (AuthException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthException(USER_UNAUTHORIZED.getMessage(), 401);
        }
    }

    public AuthPayloadDto verifyToken(String accessToken) {
        var userId = JWT_UTIL.getClaimsFromToken(accessToken, AccessTokenClaimsSet.class).getUserId();

        try {

            var authCacheData = fetchFromCache(userId);

            boolean isTokenInvalid  = sessionTokenRepository
                    .findByUserId(userId)
                    .map(SessionTokenEntity::getAccessToken)
                    .isEmpty();

            if (authCacheData == null || isTokenInvalid ) throw new AuthException(TOKEN_EXPIRED.getMessage(), 406);
            var publicKey = CERTIFICATE_KEY_UTIL.getPublicKey(authCacheData.getPublicKey());
            log.info("Public Key: {}", authCacheData.getPublicKey());


            JWT_UTIL.verifySignature(accessToken, publicKey);

            if (JWT_UTIL.isTokenExpired(authCacheData.getAccessTokenClaimsSet().getExpirationTime()))
                throw new AuthException(TOKEN_EXPIRED.getMessage(), 406);

            return AuthPayloadDto.of(userId);
        } catch (AuthException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthException(UNEXPECTED_ERROR.getMessage(), 401);
        }
    }

    private AuthResponse generateToken(String userId, int refreshTokenExpirationCount) {
        var accessTokenClaimsSet = TOKEN_MAPPER.buildAccessTokenClaimsSet(
                userId,
                JWT_UTIL.generateSessionExpirationTime(tokenExpirationProperties.getAccessToken())
        );

        var refreshTokenClaimsSet = TOKEN_MAPPER.buildRefreshTokenClaimsSet(
                userId,
                refreshTokenExpirationCount,
                JWT_UTIL.generateSessionExpirationTime(tokenExpirationProperties.getRefreshToken())
        );

        var keyPair = CERTIFICATE_KEY_UTIL.generateKeyPair();

        var authCacheData = AuthCacheData.of(
                accessTokenClaimsSet,
                encodeToString(keyPair.getPublic().getEncoded())
        );

        cacheProvider.updateToCache(authCacheData, userId, CACHE_EXPIRE_SECONDS);

        var privateKey = keyPair.getPrivate();
        var accessToken = JWT_UTIL.generateToken(accessTokenClaimsSet, privateKey);
        var refreshToken = JWT_UTIL.generateToken(refreshTokenClaimsSet, privateKey);

        return AuthResponse.of(accessToken, refreshToken);
    }

    private void verifyRefreshToken(RefreshTokenClaimsSet refreshTokenClaimsSet) {
        if (JWT_UTIL.isRefreshTokenTimeExpired(refreshTokenClaimsSet))
            throw new AuthException(TOKEN_EXPIRED.getMessage(), 401);

        if (JWT_UTIL.isRefreshTokenCountExpired(refreshTokenClaimsSet))
            throw new AuthException(REFRESH_TOKEN_COUNT_EXPIRED.getMessage(), 401);
    }

    private SessionTokenEntity fetchEntityExist(String userId) {
        return sessionTokenRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ACCESS_TOKEN_NOT_FOUND.getMessage())
        );
    }



    private AuthCacheData fetchFromCache(String cacheKey) {
        return cacheProvider.getBucket(cacheKey);
    }
}