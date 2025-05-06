package azmiu.library.service.abstraction;

import azmiu.library.criteria.CategoryCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.CategoryResponse;
import azmiu.library.model.response.PageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest, MultipartFile image);


    Long getCount();

    void addBookToCategory(Long categoryId, BookInventoryEntity bookInventoryEntity);

    List<BookResponse> getBooksByCategory(Long categoryId);

    void updateCategory (Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long categoryId);

    PageableResponse<CategoryResponse> getAllCategory(PageCriteria pageCriteria, CategoryCriteria categoryCriteria);

    CategoryEntity getCategoryEntity(Long categoryId);


}
