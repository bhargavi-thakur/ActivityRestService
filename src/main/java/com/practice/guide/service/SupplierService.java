package com.practice.guide.service;

import com.practice.guide.entity.Supplier;
import com.practice.guide.model.SupplierResponse;

import java.util.List;

public interface SupplierService {

    List<SupplierResponse> getAllSuppliers();

    List<SupplierResponse> searchSupplier(String search);
}
