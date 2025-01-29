package az.ingress.mapper;



import az.ingress.dao.entity.SessionTokenEntity;
import az.ingress.model.cache.AuthCacheData;
import az.ingress.model.jwt.AccessTokenClaimsSet;
import az.ingress.model.jwt.RefreshTokenClaimsSet;
import az.ingress.model.response.AccessTokenResponse;
import az.ingress.model.response.AuthResponse;

import java.util.Date;

import static az.ingress.model.constants.AuthConstants.ISSUER;


public enum TokenMapper {
    TOKEN_MAPPER;

    public AccessTokenClaimsSet buildAccessTokenClaimsSet(String userId, Date expirationTime) {
        return AccessTokenClaimsSet.builder()
                .iss(ISSUER)
                .userId(userId)
                .createdTime(new Date())
                .expirationTime(expirationTime)
                .build();
    }

    public AccessTokenResponse buildAccessToken(AuthResponse authResponse){
        return AccessTokenResponse.builder()
                .accessToken(authResponse.getAccessToken())
                .build();
    }

    public RefreshTokenClaimsSet buildRefreshTokenClaimsSet(String userId, int refreshTokenExpirationCount, Date expirationTime) {
        return RefreshTokenClaimsSet.builder()
                .iss(ISSUER)
                .userId(userId)
                .count(refreshTokenExpirationCount)
                .exp(expirationTime)
                .build();
    }



}