package com.practice.guide.model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ActivityResponse{
    private final Long id;
    private final String title;
    private final BigDecimal price;
    private final String currency;
    private final double rating;
    private final Boolean specialOffer;
    private final String supplierName;
}



