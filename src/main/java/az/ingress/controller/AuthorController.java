package az.ingress.controller;

import az.ingress.criteria.AuthorCriteria;
import az.ingress.criteria.PageCriteria;
import az.ingress.model.request.AuthorRequest;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public void addAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.addAuthor(authorRequest);
    }

    @GetMapping("sorted")
    public PageableResponse getAuthorSorted(PageCriteria pageCriteria,AuthorCriteria authorCriteria){
        return authorService.getAuthorSorted(pageCriteria,authorCriteria);
    }


}
