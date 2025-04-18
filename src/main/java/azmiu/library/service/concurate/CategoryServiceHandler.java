package azmiu.library.service.concurate;

import azmiu.library.configuration.CommonStatusConfig;
import azmiu.library.criteria.CategoryCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.entity.BookEntity;
import azmiu.library.dao.entity.BookInventoryEntity;
import azmiu.library.dao.entity.CategoryEntity;
import azmiu.library.dao.repository.CategoryRepository;
import azmiu.library.exception.AlreadyExistsException;
import azmiu.library.exception.ErrorMessage;
import azmiu.library.exception.NotFoundException;
import azmiu.library.exception.ResourceHasRelationsException;
import azmiu.library.mapper.CategoryMapper;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.CategoryResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.CategoryService;
import azmiu.library.service.abstraction.CommonStatusService;
import azmiu.library.service.specification.CategorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
        if (categoryRepository.existsByBookCategory(categoryRequest.getBookCategory())) {
            throw new AlreadyExistsException(ErrorMessage.CATEGORY_ALREADY_EXISTS.getMessage());
        }
        var status = commonStatusService.getCommonStatusEntity(commonStatusConfig.getActive());
        categoryRepository.save(CATEGORY_MAPPER.buildCategoryEntity(categoryRequest, status));

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
    public void updateCategory(Long id, CategoryRequest categoryRequest) {
        var categoryEntity = fetchEntityExist(id);
        CATEGORY_MAPPER.updateCategory(categoryEntity, categoryRequest);
        categoryRepository.save(categoryEntity);
    }


    @Override
    public void deleteCategory(Long categoryId) {
        var category = fetchEntityExist(categoryId);
        if (!category.getBooks().isEmpty()) {
            throw new ResourceHasRelationsException(ErrorMessage.CATEGORY_HAS_RELATED_BOOKS.getMessage());
        }
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryResponse> getAllCategory(PageCriteria pageCriteria, CategoryCriteria categoryCriteria) {
        var page = categoryRepository.findAll(
                new CategorySpecification(categoryCriteria),
                PageRequest.of(pageCriteria.getPage(),pageCriteria.getCount())
        );

        return CATEGORY_MAPPER.pageableUserResponse(page);
    }

    @Override
    public CategoryEntity getCategoryEntity(Long categoryId) {
        return fetchEntityExist(categoryId);
    }


    private CategoryEntity fetchEntityExist(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND.getMessage())
        );
    }


}
