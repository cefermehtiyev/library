package az.ingress.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "common.status")
public class CommonStatusConfig {
    private Long active;
    private Long inActive;
    private Long removed;
}

