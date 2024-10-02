package entities;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record Product(
       @NotNull(message = "id cannot be null")
       @Min(value = 1, message = "Id must be greater than 0")
        int id,

        @NotEmpty(message = "Name cannot be empty or null")
        @Size(min = 2, message = "Product name must be at least 2 characters")
        String name,

        @NotNull(message = "Category cannot be null")
        Category category,

       @Min(value = 1, message = "Rating must be at least 1")
       @Max(value = 10, message = "Rating must be at most 10")
        int rating,

        @Past(message = "Date can not be in the future")
        LocalDate createdAt,

        LocalDate modifiedAt) {


}


