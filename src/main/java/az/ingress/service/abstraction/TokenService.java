package az.ingress.service.abstraction;


import az.ingress.model.response.AuthPayloadDto;
import az.ingress.model.response.AuthResponse;

public interface TokenService {
    AuthResponse prepareToken(String userId);

    AuthResponse refreshToken(String refreshToken);

    AuthPayloadDto verifyToken(String accessToken);
}
