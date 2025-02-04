package az.ingress.service.concurate;

import az.ingress.dao.entity.BookEntity;
import az.ingress.dao.entity.CategoryEntity;
import az.ingress.dao.repository.CategoryRepository;
import az.ingress.exception.ErrorMessage;
import az.ingress.exception.NotFoundException;
import az.ingress.model.enums.CategoryStatus;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.BookResponse;
import az.ingress.model.response.CategoryResponse;
import az.ingress.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


import static az.ingress.mapper.BookMapper.BOOK_MAPPER;
import static az.ingress.mapper.CategoryMapper.CATEGORY_MAPPER;

@RequiredArgsConstructor
@Service
public class CategoryServiceHandler implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        categoryRepository.save(CATEGORY_MAPPER.buildCategoryEntity(categoryRequest));
    }

    @Override
    public void addBookToCategory(Long categoryId, BookEntity bookEntity) {
        var category = fetchEntityExist(categoryId);
        bookEntity.setCategory(category);
    }

    @Override
    public List<BookResponse> getBooksByCategory(Long categoryId) {
        var category = fetchEntityExist(categoryId);
        return category.getBookEntities().stream().map(BOOK_MAPPER::buildBookResponse).distinct().toList();
    }


    @Override
    public void deleteCategory(Long categoryId) {
        var category = fetchEntityExist(categoryId);
        category.setCategoryStatus(CategoryStatus.DELETED);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll().stream().map(CATEGORY_MAPPER::buildCategoryResponse).toList();
    }

    @Override
    public CategoryEntity getCategoryById(Long categoryId) {
        return fetchEntityExist(categoryId);
    }


    private CategoryEntity fetchEntityExist(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND.getMessage())
        );
    }


}
