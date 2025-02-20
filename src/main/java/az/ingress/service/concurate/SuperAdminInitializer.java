package az.ingress.service.concurate;

import az.ingress.configuration.BorrowStatusConfig;
import az.ingress.configuration.CommonStatusConfig;
import az.ingress.configuration.InventoryStatusConfig;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.UserRepository;
import az.ingress.mapper.BorrowStatusMapper;
import az.ingress.mapper.InventoryStatusMapper;
import az.ingress.model.enums.BorrowStatus;
import az.ingress.model.enums.CommonStatus;
import az.ingress.model.enums.InventoryStatus;
import az.ingress.model.enums.UserRole;
import az.ingress.model.request.BorrowStatusRequest;
import az.ingress.service.abstraction.BorrowStatusService;
import az.ingress.service.abstraction.CommonStatusService;
import az.ingress.service.abstraction.InventoryStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Enumerated;

import static az.ingress.mapper.BorrowStatusMapper.BORROW_STATUS_MAPPER;
import static az.ingress.mapper.CommonStatusMapper.COMMON_STATUS_MAPPER;
import static az.ingress.mapper.InventoryStatusMapper.INVENTORY_STATUS_MAPPER;
import static az.ingress.model.enums.InventoryStatus.IN_STOCK;
import static az.ingress.model.enums.InventoryStatus.LOW_STOCK;
import static az.ingress.model.enums.InventoryStatus.OUT_OF_STOCK;
import static javax.persistence.EnumType.STRING;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;
    private final BorrowStatusService borrowStatusService;
    private final BorrowStatusConfig borrowStatusConfig;
    private final InventoryStatusService inventoryStatusService;
    private final InventoryStatusConfig inventoryStatusConfig;


    private Long id;
    private String userName;
    private String email;
    private String password;
    @Enumerated(STRING)
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private String fin;

    @Override
    @Transactional
    public void run(String... args) {

        var superAdminFin = "7BNOR3C";


        if (userRepository.findByFin(superAdminFin).isEmpty()) {
            commonStatusService.addStatus(COMMON_STATUS_MAPPER.buildCommonStatusRequest(CommonStatus.ACTIVE));
            commonStatusService.addStatus(COMMON_STATUS_MAPPER.buildCommonStatusRequest(CommonStatus.INACTIVE));
            commonStatusService.addStatus(COMMON_STATUS_MAPPER.buildCommonStatusRequest(CommonStatus.REMOVED));

            inventoryStatusService.addStatus(INVENTORY_STATUS_MAPPER.buildInventoryRequest(IN_STOCK));
            inventoryStatusService.addStatus(INVENTORY_STATUS_MAPPER.buildInventoryRequest(LOW_STOCK));
            inventoryStatusService.addStatus(INVENTORY_STATUS_MAPPER.buildInventoryRequest(OUT_OF_STOCK));

            borrowStatusService.addStatus(BORROW_STATUS_MAPPER.buildBorrowStatusRequest(BorrowStatus.RETURNED));
            borrowStatusService.addStatus(BORROW_STATUS_MAPPER.buildBorrowStatusRequest(BorrowStatus.PENDING));
            borrowStatusService.addStatus(BORROW_STATUS_MAPPER.buildBorrowStatusRequest(BorrowStatus.DELAYED));

            var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
            var superAdmin = new UserEntity();
            superAdmin.setUserName("superadmin");
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setEmail("azmiusuperadmin@gmail.com");
            superAdmin.setFin(superAdminFin);
            superAdmin.setPassword(passwordEncoder.encode("azmiu123"));
            superAdmin.setUserRole(UserRole.SUPER_ADMIN);
            superAdmin.setCommonStatus(status);

            userRepository.save(superAdmin);
            log.info("Super Admin is created");
        } else {
            log.info("Super Admin already created");
        }
    }
}
