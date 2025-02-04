package az.ingress.service.concurate;


import az.ingress.dao.entity.SessionTokenEntity;
import az.ingress.dao.repository.SessionTokenRepository;
import az.ingress.exception.AuthException;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.SessionTokenMapper;
import az.ingress.mapper.TokenMapper;
import az.ingress.model.cache.AuthCacheData;
import az.ingress.model.request.AuthRequest;
import az.ingress.model.response.AuthResponse;
import az.ingress.service.abstraction.AuthService;
import az.ingress.service.abstraction.CookieService;
import az.ingress.service.abstraction.TokenService;
import az.ingress.service.abstraction.UserService;
import az.ingress.util.CacheProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static az.ingress.mapper.SessionTokenMapper.SESSION_TOKEN_MAPPER;


@Slf4j
@Service(value = "authServiceHandler")
@RequiredArgsConstructor
public class AuthServiceHandler implements AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final CookieService cookieService;
    private final CacheProvider cacheProvider;
    private final SessionTokenRepository sessionTokenRepository;

//    public AuthResponse signIn(AuthRequest authRequest) {
//        var userIdResponse = userService.getUserIdByUserNameAndPassword(authRequest);
//
//        var sessionToken = (AuthCacheData)cacheProvider.getBucket(userIdResponse.id());
//        Date currentDate = new Date();
//        System.out.println(sessionToken);
//
//        if(sessionToken != null && currentDate.before(sessionToken.getAccessTokenClaimsSet().getExpirationTime())){
//            throw new NotFoundException(ErrorMessage.TOKEN_EXPIRED.getMessage());
//        }
//
//        return  tokenService.prepareToken(userIdResponse.id());
//    }


    public AuthResponse signIn(AuthRequest authRequest) {
        var userIdResponse = userService.getUserIdByUserNameAndPassword(authRequest);

        var sessionToken = sessionTokenRepository.findByUserId(userIdResponse.id());
        Date currentDate = new Date();


        if (sessionToken.isPresent() && currentDate.before(sessionToken.get().getExpirationTime())) {
            throw new AuthException(ErrorMessage.TOKEN_EXPIRED.getMessage(), 406);

        } else if (sessionToken.isPresent() && currentDate.after(sessionToken.get().getExpirationTime())) {
            log.info("update access Token");
            var authResponse = tokenService.prepareToken(userIdResponse.id());
            var authCacheData = (AuthCacheData) cacheProvider.getBucket(userIdResponse.id());


            SESSION_TOKEN_MAPPER.updateSessionTokenEntity(sessionToken.get(), authResponse, authCacheData);
            sessionTokenRepository.save(sessionToken.get());
            return authResponse;

        } else {
            log.info("create access token");
            var authResponse = tokenService.prepareToken(userIdResponse.id());
            var authCacheData = (AuthCacheData) cacheProvider.getBucket(userIdResponse.id());
            var sessionTokenEntity = SESSION_TOKEN_MAPPER.buildSessionTokenEntity(authResponse, authCacheData);
            sessionTokenRepository.save(sessionTokenEntity);
            return authResponse;
        }


    }

    public AuthResponse refreshToken(HttpServletRequest httpServletRequest) {
        var refreshToken = cookieService.getRefreshToken(httpServletRequest);
        if (refreshToken == null) {
            throw new NotFoundException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND.getMessage());
        }
        System.out.println(refreshToken);
        log.info("Refresh token get cookie successful");
        return tokenService.refreshToken(refreshToken);
    }


    public boolean verifyToken(String accessToken) {
        log.info("AccessToken:{} ", accessToken);
        try {
            tokenService.verifyToken(accessToken);
            return true;
        } catch (Exception e) {
            log.error("Token doğrulaması uğursuz oldu: " + e.getMessage());
            return false;
        }
    }

    public SessionTokenEntity fetchEntityExist(String userId) {
        return sessionTokenRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.ACCESS_TOKEN_NOT_FOUND.getMessage())
        );
    }


}