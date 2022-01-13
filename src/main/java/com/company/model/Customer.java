package com.company.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lastname;

    private String district;

    private Double discount;

}
