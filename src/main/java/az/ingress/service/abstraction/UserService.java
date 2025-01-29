package az.ingress.service.abstraction;

import az.ingress.criteria.PageCriteria;
import az.ingress.criteria.UserCriteria;
import az.ingress.model.request.AuthRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserIdResponse;
import az.ingress.model.response.UserResponse;

public interface UserService {
    void signIn(RegistrationRequest registrationRequest);
    UserResponse getUser (Long userId);
    UserIdResponse getUserIdByUserNameAndPassword(AuthRequest authRequest);
    PageableResponse getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria);
    void updateUser(Long userId, RegistrationRequest registrationRequest);
    void deleteUser(Long userId);

}
