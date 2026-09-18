package ru.kutkovmax.yacos.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ItemResponse(
        Long id,
        String sku,
        String name,
        Integer quantity,
        BigDecimal price,
        Instant createdAt
) {
}
