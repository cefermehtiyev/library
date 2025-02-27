package azmiu.library.controller;

import azmiu.library.model.request.CategoryRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.CategoryResponse;
import azmiu.library.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void addCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.addCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public List<BookResponse> getBooksByCategory(@PathVariable Long categoryId){
        return categoryService.getBooksByCategory(categoryId);
    }

    @GetMapping
    private List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategory();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
