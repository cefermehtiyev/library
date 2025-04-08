package azmiu.library.mapper;

import azmiu.library.dao.entity.SessionTokenEntity;
import azmiu.library.model.cache.AuthCacheData;
import azmiu.library.model.response.AuthResponse;

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
