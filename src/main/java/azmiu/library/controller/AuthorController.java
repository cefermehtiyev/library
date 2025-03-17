package azmiu.library.controller;

import azmiu.library.criteria.AuthorCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.request.AuthorRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorController {
    private final AuthorService authorService;


    @PostMapping
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void addAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.addAuthor(authorRequest);
    }

    @GetMapping("/sorted")
    public PageableResponse getAuthorSorted(PageCriteria pageCriteria,AuthorCriteria authorCriteria){
        return authorService.getAuthorSorted(pageCriteria,authorCriteria);
    }

    @GetMapping("/books")
    public PageableResponse getBooksByAuthor(@RequestParam Long authorId, PageCriteria pageCriteria){
        return authorService.getBooksByAuthor(authorId,pageCriteria);
    }


}
