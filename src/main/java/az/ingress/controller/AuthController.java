package az.ingress.controller;


import az.ingress.model.request.AuthRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.AccessTokenResponse;
import az.ingress.model.response.AuthResponse;
import az.ingress.service.abstraction.AuthService;
import az.ingress.service.abstraction.CookieService;
import az.ingress.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static az.ingress.mapper.TokenMapper.TOKEN_MAPPER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CookieService cookieService;
    private final UserService userService;


    @PostMapping("/sign-in")
    @ResponseStatus(CREATED)
    public void singIn(@RequestBody RegistrationRequest registrationRequest) {
        userService.signIn(registrationRequest);
    }

    @PostMapping("/login")
    public AccessTokenResponse login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        AuthResponse authResponse = authService.signIn(authRequest);
        cookieService.addRefreshTokenCookie(response, authResponse.getRefreshToken());
        return TOKEN_MAPPER.buildAccessToken(authResponse);
    }


    @PostMapping("/refresh")
    public AccessTokenResponse refresh(HttpServletRequest request) {
        var authResponse = authService.refreshToken(request);
        return TOKEN_MAPPER.buildAccessToken(authResponse);
    }


    @PostMapping("/verify")
    public boolean verifyToken(@RequestHeader(AUTHORIZATION) String accessToken) {
        return authService.verifyToken(accessToken);
    }


}