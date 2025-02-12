package az.ingress.controller;

import az.ingress.criteria.PageCriteria;
import az.ingress.model.request.BookRequest;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.BookInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/bookInventory")
public class BookInventoryController {
    private final BookInventoryService bookInventoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void addBookToInventory(@RequestHeader(AUTHORIZATION) String accessToken, @ModelAttribute BookRequest bookRequest) {
        bookInventoryService.addBookToInventory(bookRequest);
    }

    @GetMapping("/sorted")
    public PageableResponse getBooksSortedByAddedDate(@RequestParam(defaultValue = "readCount")String sortBy,@RequestParam(defaultValue = "asc") String order,
                                                      PageCriteria pageCriteria) {
        return bookInventoryService.getBooksSorted(sortBy,order, pageCriteria);
    }

}
