package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.configuration.UserRoleConfig;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.UserRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.AuthRequest;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserIdResponse;
import azmiu.library.model.response.UserResponse;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.abstraction.TokenService;
import azmiu.library.service.abstraction.UserRoleService;
import azmiu.library.service.abstraction.UserService;
import azmiu.library.service.specification.UserSpecification;
import azmiu.library.service.strategy.RegistrationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;
import static azmiu.library.mapper.UserMapper.USER_MAPPER;

@RequiredArgsConstructor
@Service
public class UserServiceHandler implements UserService {
    private final UserRepository userRepository;
    private final RegistrationStrategy registrationStrategy;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleConfig userRoleConfig;
    private final UserRoleService userRoleService;
    private final TokenService tokenService;
    private final UserRoleServiceHandler userRoleServiceHandler;

    @Override
    @Transactional
    public void signIn(RegistrationRequest registrationRequest) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        var hashedPassword = setPasswordEncoder(registrationRequest.getPassword());
        registrationRequest.setPassword(hashedPassword);
        var userRole = userRoleService.getUserRole(registrationRequest.getRoleName());
        var userEntity = USER_MAPPER.buildUserEntity(registrationRequest, status, userRole);


        userRepository.save(userEntity);
        registrationStrategy.register(userEntity, registrationRequest);
    }

    private String setPasswordEncoder(String password){
        return passwordEncoder.encode(password);
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
        if (!passwordEncoder.matches(authRequest.getPassword(),userEntity.getPassword())){
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage());
        }

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
        return userRepository.findByUserName(authRequest.getUserName()).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );
    }
    @Override
    @Transactional
    public String getRolesFromToken(String userId) {
        var user = fetchEntityExist(Long.valueOf(userId));

        return user.getRoles().getRoleName().name();
    }


}
