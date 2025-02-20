package az.ingress.service.concurate;

import az.ingress.configuration.BorrowStatusConfig;
import az.ingress.configuration.CommonStatusConfig;
import az.ingress.configuration.UserRoleConfig;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.UserRepository;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.enums.CommonStatus;
import az.ingress.model.enums.InventoryStatus;
import az.ingress.model.enums.RoleName;
import az.ingress.service.abstraction.BorrowStatusService;
import az.ingress.service.abstraction.CommonStatusService;
import az.ingress.service.abstraction.InventoryStatusService;
import az.ingress.service.abstraction.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static az.ingress.model.enums.InventoryStatus.IN_STOCK;
import static az.ingress.model.enums.InventoryStatus.LOW_STOCK;
import static az.ingress.model.enums.InventoryStatus.OUT_OF_STOCK;

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

    @Override
    @Transactional
    public void run(String... args) {
        initializeStatusesAndRoles();
        initializeSuperAdmin();
    }


    private void initializeStatusesAndRoles() {
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
            userRoleService.addRole(RoleName.SUPER_ADMIN);
            log.info("User roles added.");
        }
    }


    private void initializeSuperAdmin() {
        var superAdminFin = "7BNOR3C";

        if (userRepository.findByFin(superAdminFin).isEmpty()) {
            var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
            var role = userRoleService.getUserRole(userRoleConfig.getSuperAdmin());

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
