package com.company.repository;

import com.company.model.Purchase;
import com.company.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasesRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "SELECT DISTINCT to_char(date, 'month') FROM purchases", nativeQuery = true)
    List<String> getDifferentMonths();

    @Query(value = "SELECT customers.lastname, stores.name FROM purchases, customers, stores WHERE purchases.customer = customers.id AND " +
            "purchases.store = stores.id", nativeQuery = true)
    List<List<String>> getCustomersLastnamesAndStoresNames();

    @Query(value = "SELECT p.date, c.lastname, c.discount, b.name, p.number" +
            " FROM purchases as p, customers as c, books as b WHERE p.customer = c.id AND p.book = b.id", nativeQuery = true)
    List<List<String>> getDateLastnameDiscountBookNameAndNumber();

    @Query(value = "SELECT p.id, c.lastname, p.date, p.total_cost FROM purchases p, customers c " +
            "WHERE p.customer = c.id AND p.total_cost >= :cost", nativeQuery = true)
    List<List<String>> getIdLastnameDateWhereTotalCostGreaterOrEqualsTo(Double cost);

    @Query(value = "SELECT c.lastname, s.district, p.date\n" +
            "FROM purchases p, customers c, stores s\n" +
            "WHERE p.customer = c.id AND p.store = s.id\n" +
            "AND c.district = s.district AND to_number(to_char(date, 'mm'),'99') >= 3 ORDER BY c.lastname, p.date", nativeQuery = true)
    List<List<String>> getLastnameDistrictDateWhereDateGreaterOrEqualsToMarchAndDistrictWhereCustomerLives();

    @Query(value = "SELECT b.name, b.stock, p.number, p.total_cost FROM purchases p, stores s, books b\n" +
            "WHERE p.book = b.id AND p.store = s.id\n" +
            "  AND b.number > 10 AND s.district = b.stock ORDER BY p.total_cost", nativeQuery = true)
    List<List<String>> getNameDistrictNumberCostWhereNumberGreater10AndDistrictEqualsStock();


    @Query(value = "SELECT * FROM stores", nativeQuery = true)
    List<Store> test();

}
