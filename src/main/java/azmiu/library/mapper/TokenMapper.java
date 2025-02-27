package azmiu.library.mapper;



import azmiu.library.model.jwt.AccessTokenClaimsSet;
import azmiu.library.model.jwt.RefreshTokenClaimsSet;
import azmiu.library.model.response.AccessTokenResponse;
import azmiu.library.model.response.AuthResponse;

import java.util.Date;

import static azmiu.library.model.constants.AuthConstants.ISSUER;


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