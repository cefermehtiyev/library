package azmiu.library.controller;

import azmiu.library.criteria.PageCriteria;
import azmiu.library.dao.repository.FileRepository;
import azmiu.library.model.request.BookRequest;
import azmiu.library.model.response.PageableResponse;
import azmiu.library.service.abstraction.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/bookInventory")
public class BookInventoryController {
    private final BookInventoryService bookInventoryService;


    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('SUPER_ADMIN') || hasRole('ADMIN')")
    public Long addBookToInventory(@RequestBody BookRequest bookRequest) {
        return bookInventoryService.addBookToInventory(bookRequest);
    }

    @GetMapping("/sorted")
    public PageableResponse getBooksSortedByAddedDate(@RequestParam(defaultValue = "readCount") String sortBy, @RequestParam(defaultValue = "asc") String order,
                                                      PageCriteria pageCriteria) {
        return bookInventoryService.getBooksSorted(sortBy, order, pageCriteria);
    }

}
