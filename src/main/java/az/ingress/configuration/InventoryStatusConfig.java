package az.ingress.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "inventory.status")
public class InventoryStatusConfig {
    private Long inStock;
    private Long stockOut;
    private Long lowStock;
}
