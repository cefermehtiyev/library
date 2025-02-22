package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.enums.BookCategory;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest);

    Long getCount();

    void addBookToCategory(Long categoryId, BookEntity bookEntity);

    List<BookResponse> getBooksByCategory(Long categoryId);

    void deleteCategory(Long categoryId);

    List<CategoryResponse> getAllCategory();

    CategoryEntity getCategoryById(Long categoryId);

}
