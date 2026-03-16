package com.practice.guide.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplierResponse {

    private Long id;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String country;

}
