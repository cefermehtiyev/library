package az.ingress.service.concurate;

import az.ingress.criteria.PageCriteria;
import az.ingress.criteria.UserCriteria;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.UserRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.enums.UserStatus;
import az.ingress.model.request.AuthRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserIdResponse;
import az.ingress.model.response.UserResponse;
import az.ingress.service.abstraction.UserService;
import az.ingress.service.specification.UserSpecification;
import az.ingress.service.strategy.RegistrationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static az.ingress.mapper.UserMapper.USER_MAPPER;

@RequiredArgsConstructor
@Service
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;
    private final RegistrationStrategy registrationStrategy;

    @Override
    public void signIn(RegistrationRequest registrationRequest) {
        var userEntity = USER_MAPPER.buildUserEntity(registrationRequest);
        userRepository.save(userEntity);
        registrationStrategy.register(userEntity,registrationRequest);
    }

    @Override
    public UserResponse getUser(Long userId) {
        return USER_MAPPER.buildUserResponse(fetchEntityExist(userId));
    }

    public UserIdResponse getUserIdByUserNameAndPassword(AuthRequest authRequest) {
        var userEntity = fetchEntityExist(authRequest);
        return USER_MAPPER.buildUserIdResponse(userEntity);
    }

    @Override
    public void updateUser(Long userId, RegistrationRequest registrationRequest) {
        var userEntity = fetchEntityExist(userId);
        USER_MAPPER.updateUser(userEntity, registrationRequest);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        var userEntity = fetchEntityExist(userId);
        userEntity.setUserStatus(UserStatus.DELETED);
        userRepository.save(userEntity);
    }

    public PageableResponse getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria) {
        var userPage = userRepository.findAll(
                new UserSpecification(userCriteria), PageRequest.of((pageCriteria.getPage()), pageCriteria.getCount(), Sort.by("id").descending())
        );
        return USER_MAPPER.pageableUserResponse(userPage);

    }


    private UserEntity fetchEntityExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );
    }

    private UserEntity fetchEntityExist(AuthRequest authRequest) {
        return userRepository.findByUserNameAndPassword(authRequest.getUserName(), authRequest.getPassword()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );
    }


}
