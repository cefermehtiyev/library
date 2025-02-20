package az.ingress.model.request;

import az.ingress.model.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.Buffer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonStatusRequest {
    private CommonStatus commonStatus;
}
