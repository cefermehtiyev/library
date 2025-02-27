package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.UserEntity;
import azmiu.library.model.request.AdminRequest;

public interface AdminService {
    void addAdmin(UserEntity userEntity , AdminRequest adminRequest);
}
