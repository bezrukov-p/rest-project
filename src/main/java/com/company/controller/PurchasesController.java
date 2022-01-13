package com.company.controller;

import com.company.exception.ResourceNotFoundException;
import com.company.model.Book;
import com.company.model.Customer;
import com.company.model.Purchase;
import com.company.model.Store;
import com.company.repository.BooksRepository;
import com.company.repository.CustomersRepository;
import com.company.repository.PurchasesRepository;
import com.company.repository.StoresRepository;
import com.company.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PurchasesController {

    @Autowired
    PurchasesRepository purchasesRepository;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    StoresRepository storesRepository;

    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    MainService mainService;

    @GetMapping("/purchases")
    public List<Purchase> getAllPurchases() {
        return purchasesRepository.findAll();
    }

    @GetMapping("/purchases/{id}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Purchase purchase = purchasesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("purchase not found for id: " + id)
        );

        return ResponseEntity.ok().body(purchase);
    }

    @PostMapping("/purchases")
    public Purchase createPurchase(@RequestBody Purchase purchase) throws ResourceNotFoundException {
        purchase.setBook(booksRepository.findById(purchase.getBook().getId()).orElseThrow(
                () -> new ResourceNotFoundException("book not found for id: " + purchase.getBook().getId())));
        purchase.setCustomer(customersRepository.findById(purchase.getCustomer().getId()).orElseThrow(
                () -> new ResourceNotFoundException("customer not found for id: " + purchase.getCustomer().getId())));
        purchase.setStore((storesRepository.findById(purchase.getStore().getId()).orElseThrow(
                () -> new ResourceNotFoundException("store not found for id: " + purchase.getStore().getId()))));
        return purchasesRepository.save(purchase);
    }

    @DeleteMapping("/purchases/{id}")
    public String deletePurchase(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        purchasesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("purchase not found for id: " + id));
        purchasesRepository.deleteById(id);

        return "deleted";
    }

    @PutMapping("/purchases/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable(value = "id") Integer id,
                                                   @RequestBody Purchase purchaseDetails) throws ResourceNotFoundException {
        Purchase purchase = purchasesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("purchase not found for id: " + id));

        purchase.setDate(purchaseDetails.getDate());
        purchase.setBook(purchaseDetails.getBook());
        purchase.setCustomer(purchaseDetails.getCustomer());
        purchase.setStore(purchaseDetails.getStore());
        purchase.setTotalCost(purchaseDetails.getTotalCost());
        purchase.setNumber(purchaseDetails.getNumber());

        final Purchase purchaseUpdated = purchasesRepository.save(purchase);

        return ResponseEntity.ok(purchaseUpdated);
    }

    @PatchMapping("/purchases/{id}")
    public ResponseEntity<Purchase> updatePurchasePartially(@PathVariable(value = "id") Integer id,
                                                            @RequestBody Purchase purchaseDetails) throws ResourceNotFoundException {
        Purchase purchase = purchasesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("purchase not found for id: " + id));

        if (purchaseDetails.getDate() != null)
            purchase.setDate(purchaseDetails.getDate());
        if (purchaseDetails.getTotalCost() != null)
            purchase.setTotalCost(purchaseDetails.getTotalCost());
        if (purchaseDetails.getNumber() != null)
            purchase.setNumber(purchaseDetails.getNumber());
        if (purchaseDetails.getBook() != null){
            Integer idBook = purchaseDetails.getBook().getId();
            Book book = booksRepository.findById(idBook).orElseThrow(
                    () -> new ResourceNotFoundException("book not found for id: " + idBook));
            purchase.setBook(book);
        }
        if (purchaseDetails.getCustomer() != null){
            Integer idCustomer = purchaseDetails.getCustomer().getId();
            Customer customer = customersRepository.findById(idCustomer).orElseThrow(
                    () -> new ResourceNotFoundException("customer not found for id: " + idCustomer));
            purchase.setCustomer(customer);
        }
        if (purchaseDetails.getStore() != null) {
            Integer idStore = purchaseDetails.getStore().getId();
            Store store = storesRepository.findById(idStore).orElseThrow(
                    () -> new ResourceNotFoundException("store not found for id: " + idStore));
            purchase.setStore(store);
        }

        final Purchase purchaseUpdated = purchasesRepository.save(purchase);

        return ResponseEntity.ok(purchaseUpdated);


    }

    @GetMapping("/purchases/get-different-months")
    public List<String> getDifferentMonths() {
        return purchasesRepository.getDifferentMonths();
    }

    @GetMapping("/purchases/get-lastnames-and-stores")
    public List<List<String>> getLastnamesAndStores() {
        return purchasesRepository.getCustomersLastnamesAndStoresNames();
    }

    @GetMapping("/purchases/get-date-lastname-discount-bookbame-and-number")
    public List<List<String>> getDateLastnameDiscountBookNameAndNumber() {
        return purchasesRepository.getDateLastnameDiscountBookNameAndNumber();
    }

    @GetMapping("/purchases/get-id-lastname-date-where-totalcost-greater-or-equals-to")
    public List<List<String>> getIdLastnameDateWhereTotalCostGreaterOrEqualsTo(@RequestParam(value = "cost") Double cost) {
        return purchasesRepository.getIdLastnameDateWhereTotalCostGreaterOrEqualsTo(cost);
    }

    @GetMapping("/purchases/get-lastname-district-date-greater-and-equals-to-march-district-where-customer-lives")
    public List<List<String>> getLastnameDistrictDateWhereDateGreaterOrEqualsToMarchAndDistrictWhereCustomerLives() {
        return purchasesRepository.
                getLastnameDistrictDateWhereDateGreaterOrEqualsToMarchAndDistrictWhereCustomerLives();
    }


    @GetMapping("/purchases/get-name-district-number-cost-where-number-greatest-10-and-dist-equals-stock")
    public List<List<String>> getNameDistrictNumberCostWhereNumberGreater10AndDistrictEqualsStock() {
        return purchasesRepository.getNameDistrictNumberCostWhereNumberGreater10AndDistrictEqualsStock();
    }
}
