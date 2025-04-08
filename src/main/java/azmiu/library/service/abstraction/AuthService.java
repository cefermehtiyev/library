package azmiu.library.service.abstraction;

import azmiu.library.model.request.AuthRequest;
import azmiu.library.model.response.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthResponse signIn(AuthRequest authRequest);

    AuthResponse refreshToken(HttpServletRequest httpServletRequest);

    boolean verifyToken(String accessToken);
}
