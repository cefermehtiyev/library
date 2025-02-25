package az.ingress.service.abstraction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void addRefreshTokenCookie(HttpServletResponse response, String refreshToken);

    String getRefreshToken(HttpServletRequest httpServletRequest);
}
