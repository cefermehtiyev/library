package azmiu.library.controller;

import azmiu.library.model.response.RatingDetailsResponse;
import azmiu.library.service.abstraction.RatingDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/rating-details")
public class RatingDetailsController {

    private final RatingDetailsService ratingDetailsService;

    @GetMapping
    public RatingDetailsResponse getUserRating(@RequestParam Long bookInventoryId){
        return ratingDetailsService.getRatingDetails(bookInventoryId);
    }
}
