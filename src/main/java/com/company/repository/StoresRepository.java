package com.company.repository;

import com.company.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoresRepository extends JpaRepository<Store, Integer> {

    List<Store> findStoresByDistrict(String district);

    @Query(value = "SELECT DISTINCT s.id, s.name, s.district, s.commission FROM purchases p, stores s, customers c " +
            "WHERE p.customer = c.id AND p.store = s.id AND s.district != 'Автозаводский' AND (c.discount BETWEEN 10 AND 15)", nativeQuery = true)
    List<Store> getStoresNotDistrictWhereCustomersBuyWithDiscount();

}
