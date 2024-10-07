package entities;

import jakarta.validation.constraints.*;
import validate.ValidId;
import validate.ValidCategory;

import java.time.LocalDate;

public record Product(

        @ValidId
        int id,

        @NotEmpty(message = "Name cannot be empty or null")
        @Size(min = 2, message = "Product name must be at least 2 characters")
        String name,

        @ValidCategory
        Category category,

        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 10, message = "Rating must be at most 10")
        int rating,

        LocalDate createdAt,

        LocalDate modifiedAt) {


}


