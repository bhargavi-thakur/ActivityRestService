package com.practice.guide.controller;

import com.practice.guide.entity.Supplier;
import com.practice.guide.model.SupplierResponse;
import com.practice.guide.service.SupplierService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/suppliers")
@RequiredArgsConstructor
public class SuppliersController {

    private final SupplierService supplierService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping(value = "/search/{search}",  produces = "application/json")
    public ResponseEntity<List<SupplierResponse>> searchSuppliers(@PathVariable String search) {
        return ResponseEntity.ok(this.supplierService.searchSupplier(search));
    }



//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @GetMapping("/suppliers")
//    public ResponseEntity<List<Supplier>> suppliers() {
//        var list = (List<Supplier>) entityManager.createNativeQuery("SELECT * FROM SUPPLIER", Supplier.class).getResultList();
//        return ResponseEntity.ok(list);
//    }
//
//    @GetMapping("/suppliers/search/{search}")
//    public ResponseEntity<List<Supplier>> suppliersSearch(@PathVariable String search) {
//        var list = (List<Supplier>) entityManager.createNativeQuery("SELECT * FROM SUPPLIER", Supplier.class).getResultList();
//        for(Supplier s: list) {
//            if(new StringBuilder().append(s.getName()).append(s.getAddress()).append(s.getZip()).append(s.getCity()).append(s.getCountry()).toString().contains(search)) {
//                return ResponseEntity.ok(List.of(s));
//            }
//        }
//        return ResponseEntity.ok(list);
//    }
}
