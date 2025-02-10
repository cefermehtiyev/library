package az.ingress.controller;

import az.ingress.criteria.PageCriteria;
import az.ingress.criteria.UserCriteria;
import az.ingress.model.request.RegistrationRequest;

import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserResponse;
import az.ingress.service.abstraction.AuthService;
import az.ingress.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void UpdateUser(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id, @RequestBody RegistrationRequest registrationRequest) {
        userService.updateUser(id, registrationRequest);
    }

    @GetMapping("{id}")
    public UserResponse getUser(@RequestHeader(AUTHORIZATION) String accessToken, @PathVariable Long id) {
        return userService.getUser(id);
    }


    @GetMapping
    public PageableResponse getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria) {
        return userService.getAllUsers(pageCriteria, userCriteria);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
