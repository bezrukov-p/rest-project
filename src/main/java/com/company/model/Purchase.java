package com.company.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "store")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    private Integer number;

    @Column(name = "total_cost")
    private Double totalCost;

}
