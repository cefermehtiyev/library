package azmiu.library.model.request;

import azmiu.library.model.enums.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonStatusRequest {
    private CommonStatus commonStatus;
}
