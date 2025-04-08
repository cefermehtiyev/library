package azmiu.library.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "user.role")
public class UserRoleConfig {
    private Long student;
    private Long employee;
    private Long admin;
    private Long superAdmin;
}
