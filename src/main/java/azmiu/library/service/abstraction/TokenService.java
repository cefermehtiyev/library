package azmiu.library.service.abstraction;


import azmiu.library.model.response.AuthPayloadDto;
import azmiu.library.model.response.AuthResponse;

public interface TokenService {
    AuthResponse prepareToken(String userId);

    AuthResponse refreshToken(String refreshToken);

    String getUserIdFromToken(String token);

    AuthPayloadDto verifyToken(String accessToken);
}
