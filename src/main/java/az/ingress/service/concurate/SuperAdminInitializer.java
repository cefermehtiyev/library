package az.ingress.service.concurate;

import az.ingress.configuration.CommonStatusConfig;
import az.ingress.dao.entity.UserEntity;
import az.ingress.dao.repository.UserRepository;
import az.ingress.model.enums.UserRole;
import az.ingress.service.abstraction.CommonStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;


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
            var superAdmin = new UserEntity();
            superAdmin.setUserName("superadmin");
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setEmail("azmiusuperadmin@gmail.com");
            superAdmin.setFin(superAdminFin);
            superAdmin.setPassword(passwordEncoder.encode("azmiu123"));
            superAdmin.setUserRole(UserRole.SUPER_ADMIN);
            var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
            superAdmin.setCommonStatus(status);

            userRepository.save(superAdmin);
            log.info("Super Admin is created");
        } else {
            log.info("Super Admin already created");
        }
    }
}
