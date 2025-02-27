package azmiu.library.controller;


import azmiu.library.model.request.AdminRequest;
import azmiu.library.model.request.AuthRequest;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.response.AccessTokenResponse;
import azmiu.library.model.response.AuthResponse;
import azmiu.library.service.abstraction.AuthService;
import azmiu.library.service.abstraction.CookieService;
import azmiu.library.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static azmiu.library.mapper.TokenMapper.TOKEN_MAPPER;
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

    @PostMapping("/admin/sign-in")
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void adminSingIn(@RequestBody AdminRequest adminRequest) {
        userService.signIn(adminRequest);
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