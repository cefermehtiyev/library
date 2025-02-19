package az.ingress.model.request;

import az.ingress.model.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonStatusRequest {
    private CommonStatus commonStatus;
}
