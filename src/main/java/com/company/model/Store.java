package com.company.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "stores")
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String district;

    private Double commission;
}
