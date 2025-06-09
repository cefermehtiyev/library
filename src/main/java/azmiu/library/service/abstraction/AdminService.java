package azmiu.library.service.abstraction;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.AdminRequest;
import azmiu.library.model.response.AdminResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserResponse;

public interface AdminService {

    void addAdmin(UserEntity userEntity , AdminRequest adminRequest);

    PageableResponse<AdminResponse> getAllAdmins(PageCriteria pageCriteria, UserCriteria userCriteria);

}
