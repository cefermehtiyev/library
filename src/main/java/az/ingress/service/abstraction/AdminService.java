package az.ingress.service.abstraction;

import az.ingress.dao.entity.UserEntity;
import az.ingress.model.request.AdminRequest;

public interface AdminService {
    void addAdmin(UserEntity userEntity , AdminRequest adminRequest);
}
