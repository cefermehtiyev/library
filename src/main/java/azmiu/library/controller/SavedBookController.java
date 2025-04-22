package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.model.response.SavedBookResponse;
import azmiu.library.service.abstraction.SavedBookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books/saved")
@FieldDefaults(makeFinal = true,level = PRIVATE)
public class SavedBookController {
    SavedBookService savedBookService;

    @PostMapping
    public void saveBook(@RequestParam Long userId, @RequestParam Long bookInventoryId){
        savedBookService.saveBook(userId, bookInventoryId);
    }
    @GetMapping
    public PageableResponse<SavedBookResponse> getUserSavedBooks(@RequestParam Long userId, PageCriteria pageCriteria){
        return savedBookService.getUserSavedBooks(userId,pageCriteria);
    }

    @DeleteMapping("{id}")
    public void deleteSavedBook(@PathVariable Long id){
        savedBookService.deleteBook(id);
    }

}
