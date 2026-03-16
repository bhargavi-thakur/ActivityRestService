package com.practice.guide.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateActivityRequest(@NotNull BigDecimal price, @NotNull Boolean specialOffer) {


}
