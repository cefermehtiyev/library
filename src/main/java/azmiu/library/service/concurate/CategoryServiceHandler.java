package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.repository.CategoryRepository;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.CategoryResponse;
import azmiu.library.service.abstraction.CategoryService;
import azmiu.library.service.abstraction.CommonStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


import static azmiu.library.mapper.BookMapper.BOOK_MAPPER;
import static azmiu.library.mapper.CategoryMapper.CATEGORY_MAPPER;

@RequiredArgsConstructor
@Service
public class CategoryServiceHandler implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CommonStatusService commonStatusService;
    private final CommonStatusConfig commonStatusConfig;

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        categoryRepository.save(CATEGORY_MAPPER.buildCategoryEntity(categoryRequest,status));
    }

    @Override
    public Long getCount() {
        return categoryRepository.count();
    }

    @Override
    public void addBookToCategory(Long categoryId, BookInventoryEntity bookInventoryEntity) {
        var category = fetchEntityExist(categoryId);
        bookInventoryEntity.setCategory(category);
    }

    @Override
    public List<BookResponse> getBooksByCategory(Long categoryId) {
        var category = fetchEntityExist(categoryId);
        return category.getBooks().stream().map(existingEntity -> BOOK_MAPPER.buildBookResponse(existingEntity.getBooks().getFirst())).toList();
    }


    @Override
    public void deleteCategory(Long categoryId) {
        var category = fetchEntityExist(categoryId);
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getRemoved());
        category.setCommonStatus(status);
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
