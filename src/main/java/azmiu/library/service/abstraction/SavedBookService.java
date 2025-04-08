package azmiu.library.service.abstraction;

import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.SavedBookResponse;

import java.util.List;

public interface SavedBookService {
    void saveBook(Long userId, Long bookInventoryId);

    List<SavedBookResponse> getUserSavedBooks(Long userId);

    void deleteBook(Long id);

}
