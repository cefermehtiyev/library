package az.ingress.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(value = "borrow.status")
public class BorrowStatusConfig {
    private Long returned;
    private Long pending;
    private Long delayed;
}
