package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.UserRepository;
import azmiu.library.model.enums.BookCategory;
import azmiu.library.model.enums.BorrowStatus;
import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.enums.InventoryStatus;
import azmiu.library.model.enums.RoleName;
import azmiu.library.service.abstraction.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static azmiu.library.mapper.CategoryMapper.CATEGORY_MAPPER;
import static azmiu.library.model.enums.RoleName.SUPER_ADMIN;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;
    private final BorrowStatusService borrowStatusService;
    private final InventoryStatusService inventoryStatusService;
    private final UserRoleService userRoleService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public void run(String... args) {
        initializeStatusesAndRoles();
        initializeSuperAdmin();
    }

    private void initializeStatusesAndRoles() {
        addCommonStatuses();
        addInventoryStatuses();
        addBorrowStatuses();
        addUserRoles();
    }

    private void addCommonStatuses() {
        if (commonStatusService.getCount() > 0) return;
        for (CommonStatus commonStatus : CommonStatus.values()) {
            commonStatusService.addStatus(commonStatus);
        }
        log.info("Common statuses added.");
    }


    private void addInventoryStatuses() {
        if (inventoryStatusService.getCount() > 0) return;
        for (InventoryStatus inventoryStatus : InventoryStatus.values()) {
            inventoryStatusService.addStatus(inventoryStatus);
        }
        log.info("Inventory statuses added.");
    }

    private void addBorrowStatuses() {
        if (borrowStatusService.getCount() > 0) return;
        for (BorrowStatus borrowStatus : BorrowStatus.values()) {
            borrowStatusService.addStatus(borrowStatus);
        }
        log.info("Borrow statuses added.");
    }

    private void addUserRoles() {
        if (userRoleService.getCount() > 0) return;
        for (RoleName role : RoleName.values()) {
            userRoleService.addRole(role);
        }
        log.info("User roles added.");
    }


    private void initializeSuperAdmin() {
        var superAdminUserName = "superadmin";

        if (userRepository.findByUserName(superAdminUserName).isEmpty()) {
            var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
            var role = userRoleService.getUserRole(SUPER_ADMIN);

            var superAdmin = UserEntity.builder()
                    .userName("superadmin")
                    .firstName("Super")
                    .lastName("Admin")
                    .email("azmiusuperadmin@gmail.com")
                    .fin(superAdminUserName)
                    .password(passwordEncoder.encode("azmiu123"))
                    .userRole(role)
                    .commonStatus(status)
                    .build();

            userRepository.save(superAdmin);
            log.info("Super Admin created successfully.");
        } else {
            log.info("Super Admin already exists.");
        }
    }
}
