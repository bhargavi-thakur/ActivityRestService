package com.practice.guide.repository;

import com.practice.guide.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    // Native Query is plan sql that uses table name and column
    public final String SUPPLIER_SEARCH_QUERY = """
            SELECT * FROM SUPPLIER WHERE LOWER(name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(address) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(zip) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(city) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(country) LIKE LOWER(CONCAT('%', :search, '%'))
            """;

    //nativeQuery = true means it is not JPQL
    @Query(value = SUPPLIER_SEARCH_QUERY,nativeQuery = true)
    List<Supplier> searchSupplier(String search);

    /***
    JPQL
    public final String SUPPLIER_SEARCH_QUERY = """
            SELECT s FROM Supplier s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(s.address) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(s.zip) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(s.city) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(s.country) LIKE LOWER(CONCAT('%', :search, '%'))
            """;

    @Query(SUPPLIER_SEARCH_QUERY)
    List<Supplier> searchSupplier(@Param("search") String search);
    ***/
}
