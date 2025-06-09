package azmiu.library.model.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationMessages {
    public static final String FIELD_CANNOT_BE_NULL = "Field cannot be null";
    public static final String RATING_MIN_VALUE = "Rating must be at least 1";
    public static final String RATING_MAX_VALUE = "Rating cannot be more than 5";}
