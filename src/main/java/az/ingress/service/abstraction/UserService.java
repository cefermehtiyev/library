package az.ingress.service.abstraction;

import az.ingress.criteria.PageCriteria;
import az.ingress.criteria.UserCriteria;
import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.AuthRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserIdResponse;
import az.ingress.model.response.UserResponse;

import java.util.List;

public interface UserService {
    void signIn(RegistrationRequest registrationRequest);
    UserResponse getUser (Long userId);
    UserEntity getUserEntity(Long userId);
    UserEntity getUserEntityByFin(String fin);
    UserIdResponse getUserIdByUserNameAndPassword(AuthRequest authRequest);
    PageableResponse getAllBooksByFin(String fin,PageCriteria pageCriteria);
    PageableResponse getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria);
    void updateUser(Long userId, RegistrationRequest registrationRequest);
    void deleteUser(Long userId);

}
