package com.practice.guide.service.impl;

import com.practice.guide.entity.Supplier;
import com.practice.guide.exception.DataNotFoundException;
import com.practice.guide.model.SupplierResponse;
import com.practice.guide.repository.SupplierRepository;
import com.practice.guide.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;


    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSuppliers() {
        List<SupplierResponse> supplierResponseList = new ArrayList<>();
        List<Supplier> suppliers = this.supplierRepository.findAll();
        suppliers.forEach(supplier -> {
                    SupplierResponse response = toSupplierResponse(supplier);
                    supplierResponseList.add(response);
                    });
        return supplierResponseList;

    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> searchSupplier(String search) {
        List<SupplierResponse> supplierResponseList = new ArrayList<>();
        List<Supplier> suppliers = this.supplierRepository.searchSupplier(search);
        if(suppliers.isEmpty()){
            throw new DataNotFoundException("Supplier not found");
        }
        suppliers.forEach(supplier -> {
            SupplierResponse response = toSupplierResponse(supplier);
            supplierResponseList.add(response);
        });
        return supplierResponseList;
    }

    private static SupplierResponse toSupplierResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .zip(supplier.getZip())
                .city(supplier.getCity())
                .country(supplier.getCountry())
                .build();
    }


}
