package az.ingress.service.abstraction;

import az.ingress.model.request.AuthRequest;
import az.ingress.model.response.AuthResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthResponse signIn(AuthRequest authRequest);

    AuthResponse refreshToken(HttpServletRequest httpServletRequest);

    boolean verifyToken(String accessToken);
}
