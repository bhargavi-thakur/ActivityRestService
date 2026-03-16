package com.practice.guide.controller;

import com.practice.guide.helpers.SupplierHelper;
import com.practice.guide.model.SupplierResponse;
import com.practice.guide.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SuppliersController.class)
class SuppliersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SupplierService supplierService;

    @Test
    void getAllSuppliers_success() throws Exception {
        when(this.supplierService.getAllSuppliers()).thenReturn(createSupplierResponse());
        mockMvc.perform(get("/suppliers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].name").value("James"))
                .andExpect(jsonPath("$[1].name").value("Jhon"));

        verify(this.supplierService,times(1)).getAllSuppliers();
    }

    @Test
    void getAllSuppliers_db_connection_exception() throws Exception {
        when(this.supplierService.getAllSuppliers()).thenThrow(new DataAccessResourceFailureException("DB not reachable"));
        mockMvc.perform(get("/suppliers"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.message").value("DB not reachable"));
        verify(this.supplierService,times(1)).getAllSuppliers();
    }

    @Test
    void searchSuppliers_success() throws Exception {
        when(this.supplierService.searchSupplier(anyString())).thenReturn(createSupplierResponse());
        //here tried using anyString() but it was putting null value as it is not mockito, it only works inside when and verify
        mockMvc.perform(get("/suppliers/search/{search}","USA"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].name").value("James"))
                .andExpect(jsonPath("$[1].name").value("Jhon"));
        verify(this.supplierService,times(1)).searchSupplier(anyString());
    }

    @Test
    void searchSupplier_shouldReturn404_whenPathVariableMissing() throws Exception {
        when(this.supplierService.searchSupplier(anyString())).thenReturn(createSupplierResponse());
        mockMvc.perform(get("/suppliers/search/{search}",""))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(this.supplierService,times(0)).searchSupplier(anyString());
    }

    private static List<SupplierResponse> createSupplierResponse() {
        return  List.of(SupplierHelper.createSupplierResponse(1L,"James","Dreispitz 47","Zurich")
                ,SupplierHelper.createSupplierResponse(2L,"Jhon","Birch 47","Zurich"));
    }
}