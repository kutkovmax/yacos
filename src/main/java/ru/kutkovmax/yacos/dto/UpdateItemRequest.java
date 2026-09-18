package ru.kutkovmax.yacos.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateItemRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @NotNull(message = "Quantity is required")
        @PositiveOrZero(message = "Quantity must be greater than or equal to 0")
        Integer quantity,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        BigDecimal price

) {
}
