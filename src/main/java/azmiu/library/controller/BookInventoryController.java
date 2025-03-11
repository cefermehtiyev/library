package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.repository.FileRepository;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/bookInventory")
public class BookInventoryController {
    private final BookInventoryService bookInventoryService;


    @PostMapping(name = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('SUPER_ADMIN') || hasRole('ADMIN')")
    public void addBookToInventory(@ModelAttribute BookRequest bookRequest,
                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                   @RequestParam(value = "image", required = false) MultipartFile image) {
        bookInventoryService.addBookToInventory(bookRequest, file, image);
    }


    @GetMapping("/sorted")
    public PageableResponse getBooksSortedByAddedDate(@RequestParam(defaultValue = "readCount") String sortBy, @RequestParam(defaultValue = "asc") String order,
                                                      PageCriteria pageCriteria) {
        return bookInventoryService.getBooksSorted(sortBy, order, pageCriteria);
    }

}
