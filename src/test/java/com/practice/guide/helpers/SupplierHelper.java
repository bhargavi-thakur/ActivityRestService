package com.practice.guide.helpers;

import com.practice.guide.entity.Supplier;
import com.practice.guide.model.SupplierResponse;

public class SupplierHelper {

    public static Supplier createSupplier(Long id,String name,String address,String city){
        Supplier supplier = new Supplier();
        supplier.setId(id);
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setCity(city);
        return supplier;
    }

    public static SupplierResponse createSupplierResponse(Long id, String name, String address, String city){
        return SupplierResponse.builder()
                .id(id)
                .name(name)
                .address(address)
                .city(city)
                .build();
    }
}

