package azmiu.library.service.abstraction;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.SavedBookResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SavedBookService {
    void saveBook(Long userId, Long bookInventoryId);

    PageableResponse<SavedBookResponse> getUserSavedBooks(Long userId, PageCriteria pageCriteria);

    void deleteBook(Long id);

}
