package com.practice.guide.service.impl;

import com.practice.guide.entity.Supplier;
import com.practice.guide.exception.DataNotFoundException;
import com.practice.guide.helpers.SupplierHelper;
import com.practice.guide.model.SupplierResponse;
import com.practice.guide.repository.SupplierRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    void getAllSuppliers_success() {
        when(this.supplierRepository.findAll()).thenReturn(getSuppliers());
        List<SupplierResponse> suppliers = supplierService.getAllSuppliers();
        assertNotNull(suppliers);
        assertEquals(2,suppliers.size());
        verify(this.supplierRepository,times(1)).findAll();
    }

    @Test
    void getAllSuppliers_DataAccessResourceFailureException() {
        doThrow(new DataAccessResourceFailureException("DB Connection Failure")).when(this.supplierRepository).findAll();
        DataAccessResourceFailureException dataAccessResourceFailureException = assertThrows(DataAccessResourceFailureException.class, () -> supplierService.getAllSuppliers());
        assertEquals("DB Connection Failure",dataAccessResourceFailureException.getMessage());
        verify(this.supplierRepository,times(1)).findAll();
    }

    @Test
    void searchSupplier() {
        when(this.supplierRepository.searchSupplier(anyString())).thenReturn(getSuppliers());
        List<SupplierResponse> suppliers = supplierService.searchSupplier(anyString());
        assertNotNull(suppliers);
        assertEquals(2,suppliers.size());
        verify(this.supplierRepository,times(1)).searchSupplier(anyString());
    }

    @Test
    void searchSupplier_DataNotFoundException() {
        when(this.supplierRepository.searchSupplier(anyString())).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> supplierService.searchSupplier(anyString()))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("Supplier not found");
        verify(this.supplierRepository,times(1)).searchSupplier(anyString());
    }

    private static List<Supplier> getSuppliers() {
        return List.of(
                SupplierHelper.createSupplier(1L, "Jhon Watler", "Birch Street 01", "Lausane"),
                SupplierHelper.createSupplier(2L, "James Wiss", "Street 01", "Zurich"));
    }
}