package az.ingress.controller;

import az.ingress.model.request.AuthorRequest;
import az.ingress.service.abstraction.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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


}
