package azmiu.library.controller;

import azmiu.library.criteria.BookCriteria;
import azmiu.library.criteria.PageCriteria;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.BookResponse;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class BookController {
    private final BookService bookService;


    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }



    @GetMapping("/sorted")
    public PageableResponse<BookResponse> getAllBooks( @RequestParam(defaultValue = "pages") String sortBy, @RequestParam(defaultValue = "asc") String order,PageCriteria pageCriteria,BookCriteria bookCriteria) {
        return bookService.getAllBooks(sortBy,order,pageCriteria, bookCriteria);
    }

//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateBook(@RequestParam Long id, @ModelAttribute BookRequest bookRequest ,
                           @RequestParam(value = "file", required = false) MultipartFile file,
                           @RequestParam(value = "image", required = false) MultipartFile image) {
        bookService.updateBook(id, bookRequest, file, image);
    }


    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('SUPER_ADMIN')||hasRole('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
