package az.ingress.service.abstraction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {
    void addRefreshTokenCookie(HttpServletResponse response, String refreshToken);

    String getRefreshToken(HttpServletRequest httpServletRequest);
}
