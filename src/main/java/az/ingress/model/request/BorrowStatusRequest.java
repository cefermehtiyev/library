package az.ingress.model.request;

import az.ingress.model.enums.BorrowStatus;
import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowStatusRequest {
    private BorrowStatus borrowStatus;
}
