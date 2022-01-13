package com.company.controller;

import com.company.exception.ResourceNotFoundException;
import com.company.model.Store;
import com.company.repository.StoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest")
public class StoresController {

    @Autowired
    StoresRepository storesRepository;

    @GetMapping("/stores")
    public List<Store> getAllStores() {
        return storesRepository.findAll();
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Store store = storesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("store not found for id: " + id)
        );

        return ResponseEntity.ok().body(store);
    }

    @PostMapping("/stores")
    public Store createStore(@RequestBody Store store){
        return storesRepository.save(store);
    }

    @DeleteMapping("/stores/{id}")
    public String deleteStore(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        storesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("store not found for id: " + id));
        storesRepository.deleteById(id);

        return "deleted";
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable(value = "id") Integer id,
                             @RequestBody Store storeDetails) throws ResourceNotFoundException {
        Store store = storesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("store not found for id: " + id));

        store.setName(storeDetails.getName());
        store.setDistrict(storeDetails.getDistrict());
        store.setCommission(storeDetails.getCommission());

        final Store storeUpdated = storesRepository.save(store);

        return ResponseEntity.ok(storeUpdated);

    }

    @PatchMapping("/stores/{id}")
    public ResponseEntity<Store> updateStorePartially(@PathVariable(value = "id") Integer id,
                                                      @RequestBody Store storeDetails) throws ResourceNotFoundException {
        Store store = storesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("store not found for id: " + id));

        if (storeDetails.getName() != null)
            store.setName(storeDetails.getName());
        if (storeDetails.getDistrict() != null)
            store.setDistrict(storeDetails.getDistrict());
        if (storeDetails.getCommission() != null)
            store.setCommission(storeDetails.getCommission());

        final Store storeUpdated = storesRepository.save(store);

        return ResponseEntity.ok(storeUpdated);
    }



    @GetMapping("/stores/names-stores-from-districts")
    public List<String> getNamesStoresFromDistricts(@RequestParam List<String> districts) {
        Set<String> districtsSet = new HashSet<>(districts);

        List<String> namesOfStores= new ArrayList<>();

        for (String district : districtsSet){
            List<Store> stores = storesRepository.findStoresByDistrict(district);
            for(Store store: stores){
                namesOfStores.add(store.getName());
            }
        }

        return namesOfStores;
    }

    @GetMapping("/stores/get-stores-not-district-where-customer-buy-with-discount")
    public List<Store> getStoresNotDistrictWhereCustomersBuyWithDiscount() {
        return storesRepository.getStoresNotDistrictWhereCustomersBuyWithDiscount();
    }

}
