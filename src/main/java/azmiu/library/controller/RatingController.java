package azmiu.library.controller;

import azmiu.library.model.request.RatingRequest;
import azmiu.library.model.response.RatingDetailsResponse;
import azmiu.library.model.response.RatingResponse;
import azmiu.library.service.abstraction.RatingDetailsService;
import azmiu.library.service.abstraction.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/ratings")
public class RatingController {
    private final RatingService ratingService;
    private final RatingDetailsService ratingDetailsService;

    @PostMapping
    public void insertOrUpdateRating(@Valid @RequestBody RatingRequest ratingRequest){
        ratingService.insertOrUpdateRating(ratingRequest);
    }

    @DeleteMapping
    public void removeRating(@RequestParam Long bookInventoryId, @RequestParam Long userId){
        ratingService.removeRating(bookInventoryId,userId);
    }

    @GetMapping
    public RatingResponse getUserRating(@RequestParam Long bookInventoryId, @RequestParam Long userId){
        return ratingService.getUserRating(bookInventoryId,userId);
    }


}
