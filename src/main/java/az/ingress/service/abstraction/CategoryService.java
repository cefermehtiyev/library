package az.ingress.service.abstraction;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.enums.BookCategory;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.BookResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest);

    void addBookToCategory(Long categoryId, BookEntity bookEntity);

    List<BookResponse> getBooksByCategory(Long categoryId);

    void deleteCategory(Long categoryId);
    CategoryEntity getCategoryById(Long categoryId);

}
