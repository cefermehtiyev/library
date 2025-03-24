package azmiu.library.service.abstraction;

import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest);

    Long getCount();

    void addBookToCategory(Long categoryId, BookInventoryEntity bookInventoryEntity);

    List<BookResponse> getBooksByCategory(Long categoryId);

    void deleteCategory(Long categoryId);

    List<CategoryResponse> getAllCategory();

    CategoryEntity getCategoryEntity(Long categoryId);


}
