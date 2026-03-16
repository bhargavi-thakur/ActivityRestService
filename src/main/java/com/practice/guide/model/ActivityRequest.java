package com.practice.guide.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ActivityRequest(@NotBlank String title, @NotNull BigDecimal price, @NotBlank String currency, Double rating,
                              Boolean specialOffer,
                              @NotNull Long supplierId) {

}
