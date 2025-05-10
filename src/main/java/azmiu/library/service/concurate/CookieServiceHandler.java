package azmiu.library.service.concurate;

import azmiu.library.service.abstraction.CookieService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieServiceHandler implements CookieService {

    @Override
    public void addRefreshTokenCookie(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        httpServletResponse.addCookie(refreshTokenCookie);
    }

    @Override
    public String getRefreshToken(HttpServletRequest httpServletRequest) {
        return getRefreshTokenCookie(httpServletRequest);
    }

    private String getRefreshTokenCookie(HttpServletRequest httpServletRequest) {
        var cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();

                }
            }
        }
        return null;

    }
}
