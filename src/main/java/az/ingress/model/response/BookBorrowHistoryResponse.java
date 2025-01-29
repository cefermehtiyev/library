package az.ingress.model.response;

import az.ingress.model.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookBorrowHistoryResponse {
    private BookResponse bookResponse;
    private BorrowStatus borrowStatus;
    private LocalDate borrowDate;
    private LocalDate returnedDate;
}
