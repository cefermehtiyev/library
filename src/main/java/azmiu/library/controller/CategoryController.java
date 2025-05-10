package azmiu.library.controller;

import azmiu.library.criteria.CategoryCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.CategoryResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public void addCategory(@ModelAttribute CategoryRequest categoryRequest,
                            @RequestParam(value = "image", required = false) MultipartFile image){
        categoryService.addCategory(categoryRequest, image);
    }

    @GetMapping("/{categoryId}")
    public List<BookResponse> getBooksByCategory(@PathVariable Long categoryId){
        return categoryService.getBooksByCategory(categoryId);
    }

    @PutMapping("/update/{id}")
    public void updateCategory(@PathVariable Long id, CategoryRequest categoryRequest){
        categoryService.updateCategory(id, categoryRequest);
    }

    @GetMapping
    public PageableResponse<CategoryResponse> getAllCategories(PageCriteria pageCriteria, CategoryCriteria categoryCriteria){
        return categoryService.getAllCategory(pageCriteria, categoryCriteria);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
