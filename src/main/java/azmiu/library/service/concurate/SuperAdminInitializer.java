package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.configuration.UserRoleConfig;
import azmiu.library.dao.entity.UserEntity;
import azmiu.library.dao.repository.UserRepository;
import azmiu.library.model.enums.BookCategory;
import azmiu.library.model.enums.BorrowStatus;
import azmiu.library.model.enums.CommonStatus;
import azmiu.library.model.enums.RoleName;
import azmiu.library.service.abstraction.BorrowStatusService;
import azmiu.library.service.abstraction.CategoryService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.abstraction.InventoryStatusService;
import azmiu.library.service.abstraction.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static azmiu.library.mapper.CategoryMapper.CATEGORY_MAPPER;
import static azmiu.library.model.enums.InventoryStatus.IN_STOCK;
import static azmiu.library.model.enums.InventoryStatus.LOW_STOCK;
import static azmiu.library.model.enums.InventoryStatus.OUT_OF_STOCK;
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
    private final UserRoleConfig userRoleConfig;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public void run(String... args) {
        initializeStatusesAndRoles();
        initializeSuperAdmin();
    }


    private void initializeStatusesAndRoles() {
        if(categoryService.getCount() == null){
            categoryService.addCategory(CATEGORY_MAPPER.buildCategoryRequest(BookCategory.HISTORY));
            categoryService.addCategory(CATEGORY_MAPPER.buildCategoryRequest(BookCategory.NOVEL));
            categoryService.addCategory(CATEGORY_MAPPER.buildCategoryRequest(BookCategory.SCIENCE));


        }

        if (commonStatusService.getCount() == 0) {
            commonStatusService.addStatus(CommonStatus.ACTIVE);
            commonStatusService.addStatus(CommonStatus.INACTIVE);
            commonStatusService.addStatus(CommonStatus.REMOVED);
            log.info("Common statuses added.");
        }

        if (inventoryStatusService.getCount() == 0) {
            inventoryStatusService.addStatus(IN_STOCK);
            inventoryStatusService.addStatus(LOW_STOCK);
            inventoryStatusService.addStatus(OUT_OF_STOCK);
            log.info("Inventory statuses added.");
        }

        if (borrowStatusService.getCount() == 0) {
            borrowStatusService.addStatus(BorrowStatus.RETURNED);
            borrowStatusService.addStatus(BorrowStatus.PENDING);
            borrowStatusService.addStatus(BorrowStatus.DELAYED);
            log.info("Borrow statuses added.");
        }

        if (userRoleService.getCount() == 0) {
            userRoleService.addRole(RoleName.STUDENT);
            userRoleService.addRole(RoleName.EMPLOYEE);
            userRoleService.addRole(RoleName.ADMIN);
            userRoleService.addRole(SUPER_ADMIN);
            log.info("User roles added.");
        }
    }


    private void initializeSuperAdmin() {
        var superAdminFin = "7BNOR3C";

        if (userRepository.findByFin(superAdminFin).isEmpty()) {
            var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
            var role = userRoleService.getUserRole(SUPER_ADMIN);

            var superAdmin = new UserEntity();
            superAdmin.setUserName("superadmin");
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setEmail("azmiusuperadmin@gmail.com");
            superAdmin.setFin(superAdminFin);
            superAdmin.setPassword(passwordEncoder.encode("azmiu123"));
            superAdmin.setRoles(role);
            superAdmin.setCommonStatus(status);

            userRepository.save(superAdmin);
            log.info("Super Admin created successfully.");
        } else {
            log.info("Super Admin already exists.");
        }
    }
}
