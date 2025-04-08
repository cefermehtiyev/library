package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.criteria.UserCriteria;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.UserRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.enums.RoleName;
import azmiu.library.model.request.AuthRequest;
import azmiu.library.model.request.RegistrationRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.UserIdResponse;
import azmiu.library.model.response.UserResponse;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.abstraction.UserRoleService;
import azmiu.library.service.abstraction.UserService;
import azmiu.library.service.specification.UserSpecification;
import azmiu.library.service.strategy.RegistrationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final UserRoleService userRoleService;

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
        return USER_MAPPER.buildUserResponse(findById(userId));
    }

    @Override
    public UserEntity getUserEntity(Long userId) {
        return findById(userId);
    }

    @Override
    public UserEntity getUserEntityByUserName(String userName) {
        return findByUserName(userName);
    }

    @Override
    public UserIdResponse getUserIdByUserNameAndPassword(AuthRequest authRequest) {

        var userEntity = findByUserName(authRequest.getUserName());
        if (!passwordEncoder.matches(authRequest.getPassword(),userEntity.getPassword())){
            throw new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage());
        }

        return USER_MAPPER.buildUserIdResponse(userEntity);
    }

    @Override
    public void updateUser(Long userId, RegistrationRequest registrationRequest) {
        var userEntity = findById(userId);
        USER_MAPPER.updateUser(userEntity, registrationRequest);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        var userEntity = findById(userId);
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getRemoved());
        userEntity.setCommonStatus(status);
        userRepository.save(userEntity);
    }

    @Override
    public PageableResponse<UserResponse> getAllUsers(PageCriteria pageCriteria, UserCriteria userCriteria) {
        var userPage = userRepository.findAll(
                new UserSpecification(userCriteria),
                PageRequest.of((pageCriteria.getPage()), pageCriteria.getCount(), Sort.by("id").ascending())
        );

        return USER_MAPPER.pageableUserResponse(userPage);

    }

    @Override
    public PageableResponse<UserResponse> getAllAdmins(PageCriteria pageCriteria, UserCriteria userCriteria) {
        var userPage = userRepository.findAll(
                new UserSpecification(userCriteria)).stream()
                .filter(userEntity -> userEntity.getUserRole().getRoleName().equals(RoleName.ADMIN)).toList();

        long totalElements = userPage.size();
        int pageSize = pageCriteria.getCount();
        int currentPage = pageCriteria.getPage();
        int fromIndex = currentPage * pageSize;

        int toIndex = Math.min(fromIndex +pageSize ,(int) totalElements);
        List<UserEntity> pagedUser = userPage.subList(fromIndex,toIndex);
        Pageable pageable = PageRequest.of(currentPage,pageSize);
        Page<UserEntity> page = new PageImpl<>(pagedUser,pageable,totalElements);
        return USER_MAPPER.pageableUserResponse(page);
    }


    private UserEntity findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage())
        );
    }

    private UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(
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



    @Override
    @Transactional
    public String getRolesFromToken(String userId) {
        var user = findById(Long.valueOf(userId));
        return user.getUserRole().getRoleName().name();
    }


}
