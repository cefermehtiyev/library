package az.ingress.mapper;

import az.ingress.dao.entity.SessionTokenEntity;
import az.ingress.model.cache.AuthCacheData;
import az.ingress.model.response.AuthResponse;

public enum SessionTokenMapper {
    SESSION_TOKEN_MAPPER;

    public SessionTokenEntity buildSessionTokenEntity(AuthResponse authResponse, AuthCacheData authCacheData) {
        return SessionTokenEntity.builder()
                .userId(authCacheData.getAccessTokenClaimsSet().getUserId())
                .accessToken(authResponse.getAccessToken())
                .expirationTime(authCacheData.getAccessTokenClaimsSet().getExpirationTime())
                .isActive(true)
                .build();
    }

    public void updateSessionTokenEntity(SessionTokenEntity sessionToken, AuthResponse authResponse, AuthCacheData cacheData) {
        sessionToken.setAccessToken(authResponse.getAccessToken());
        sessionToken.setExpirationTime(cacheData.getAccessTokenClaimsSet().getExpirationTime());

    }
}
