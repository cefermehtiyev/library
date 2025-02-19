package az.ingress.service.concurate;

import az.ingress.configuration.CommonStatusConfig;
import az.ingress.criteria.PageCriteria;
import az.ingress.criteria.UserCriteria;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.UserRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.request.AuthRequest;
import az.ingress.model.request.RegistrationRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.model.response.UserIdResponse;
import az.ingress.model.response.UserResponse;
import az.ingress.service.abstraction.CommonStatusService;
import az.ingress.service.abstraction.UserService;
import az.ingress.service.specification.UserSpecification;
import az.ingress.service.strategy.RegistrationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.awt.print.Pageable;
import java.util.List;

import static az.ingress.mapper.BookMapper.BOOK_MAPPER;
import static az.ingress.mapper.UserMapper.USER_MAPPER;

@RequiredArgsConstructor
@Service
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;
    private final RegistrationStrategy registrationStrategy;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;

    @Override
    @Transactional
    public void signIn(RegistrationRequest registrationRequest) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var userEntity = USER_MAPPER.buildUserEntity(registrationRequest, status);
        userRepository.save(userEntity);
        registrationStrategy.register(userEntity, registrationRequest);
    }

    @Override
    public UserResponse getUser(Long userId) {
        return USER_MAPPER.buildUserResponse(fetchEntityExist(userId));
    }

    @Override
    public UserEntity getUserEntity(Long userId) {
        return fetchEntityExist(userId);
    }

    @Override
    public UserEntity getUserEntityByFin(String fin) {
        return fetchEntityExist(fin);
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
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        userEntity.setCommonStatus(status);
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

    private UserEntity fetchEntityExist(String fin) {
        return userRepository.findByFin(fin).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );
    }

    @Override
    public PageableResponse getAllBooksByFin(String fin, PageCriteria pageCriteria) {
        var page = userRepository.findBooksByFin(
                fin, PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount())
        );
        return BOOK_MAPPER.pageableBookResponse(page);
    }


    private UserEntity fetchEntityExist(AuthRequest authRequest) {
        return userRepository.findByUserNameAndPassword(authRequest.getUserName(), authRequest.getPassword()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );
    }


}
