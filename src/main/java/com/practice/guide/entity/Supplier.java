package com.practice.guide.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SUPPLIER")
@NoArgsConstructor
public class Supplier {

    @Id
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "zip")
    private String zip;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Activity> activities = new ArrayList<>();
}
