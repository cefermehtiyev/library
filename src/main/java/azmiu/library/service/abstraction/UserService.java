package azmiu.library.service.abstraction;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.AuthRequest;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserIdResponse;
import azmiu.library.model.response.UserResponse;

public interface UserService {
    void signIn(RegistrationRequest registrationRequest);

    UserResponse getUser(Long userId);

    UserEntity getUserEntity(Long userId);

    UserEntity getUserEntityByUserName(String userName);

    UserIdResponse getUserIdByUserNameAndPassword(AuthRequest authRequest);

    PageableResponse getAllBooksByFin(String fin, PageCriteria pageCriteria);

    PageableResponse getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria);

    PageableResponse getAllAdmins(PageCriteria pageCriteria, UserCriteria userCriteria);

    String getRolesFromToken(String userId);

    void updateUser(Long userId, RegistrationRequest registrationRequest);

    void deleteUser(Long userId);

}
