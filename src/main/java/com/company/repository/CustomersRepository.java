package com.company.repository;

import com.company.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT district FROM customers GROUP BY district", nativeQuery = true)
    List<String> getDifferentCustomersDistricts();

    List<Customer> findCustomersByDistrict(String district);

}
