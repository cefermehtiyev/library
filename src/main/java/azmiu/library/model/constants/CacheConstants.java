package azmiu.library.model.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class CacheConstants {
    public static final String CACHE_PREFIX = "auth:";
    public static final Long CACHE_EXPIRE_SECONDS = 1200L;
    public static final String BOOK_CACHE_KEY = "rating:bookInventory-id:";
}