package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.model.request.RegistrationRequest;

import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserResponse;
import azmiu.library.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void UpdateUser(@PathVariable Long id, @RequestBody RegistrationRequest registrationRequest) {
        userService.updateUser(id, registrationRequest);
    }

    @GetMapping("{id}")
    public UserResponse getUser( @PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/by-fin")
    public PageableResponse getAllBooksByFin(@RequestParam String fin, PageCriteria pageCriteria) {
        return userService.getAllBooksByFin(fin, pageCriteria);
    }


    @GetMapping
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public PageableResponse<UserResponse> getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria) {
        return userService.getAllUsers(pageCriteria, userCriteria);
    }

    @GetMapping("/all-admins")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public PageableResponse<UserResponse> getAllAdmins(PageCriteria pageCriteria, UserCriteria userCriteria) {
        return userService.getAllAdmins(pageCriteria, userCriteria);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
