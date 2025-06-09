package azmiu.library.mapper;

import azmiu.library.model.dto.RatingCacheDto;

import java.math.BigDecimal;

public enum CacheDtoMapper {
    CACHE_DTO_MAPPER;

    public RatingCacheDto buildCacheDto(Integer voteCount, BigDecimal averageRating){
        return RatingCacheDto.builder()
                .voteCount(voteCount)
                .averageRating(averageRating)
                .build();

    }
}
