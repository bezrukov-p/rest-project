package com.company.service;

import com.company.model.Book;
import com.company.model.Customer;
import com.company.model.Purchase;
import com.company.model.Store;
import com.company.repository.BooksRepository;
import com.company.repository.CustomersRepository;
import com.company.repository.PurchasesRepository;
import com.company.repository.StoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MainService {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    StoresRepository storesRepository;

    @Autowired
    CustomersRepository customersRepository;

    @Autowired
    PurchasesRepository purchasesRepository;

    public List<String> getBooksDifferentNamesAndPrice(){
        List<String> result = new LinkedList<>(booksRepository.getDifferentBookTitles());
        List<Double> prices = booksRepository.getDifferentBookPrices();
        for(Double price : prices){
            result.add(price.toString());
        }

        return result;
    }

    public List<List<String>> getLastnameAndDiscountLivesInDistrict(String district) {
        List<Customer> customers = customersRepository.findCustomersByDistrict(district);

        List<List<String>> lastnamesAndDiscounts = new ArrayList<>();

        for (Customer customer : customers) {
            ArrayList<String> lastnameAndDiscount = new ArrayList<>();
            lastnameAndDiscount.add(customer.getLastname());
            lastnameAndDiscount.add(customer.getDiscount().toString());
            lastnamesAndDiscounts.add(lastnameAndDiscount);
        }

        return lastnamesAndDiscounts;
    }

    public List<List<String>> getNamesAndPricesOfBooksByNameContainsOrPriceGreaterThan
            (String name, Double price) {
        List<Book> books = booksRepository.getBooksByNameContainingOrPriceGreaterThan(name, price);

        books.sort((o1, o2) -> {
            if (o1.getName().compareTo(o2.getName()) == 0)
                return -o1.getPrice().compareTo(o2.getPrice());
            return o1.getName().compareTo((o2.getName()));
        });

        List<List<String>> result = new ArrayList<>();

        for (Book book : books) {
            List<String> nameAndPrice = new ArrayList<>();
            nameAndPrice.add(book.getName());
            nameAndPrice.add(book.getPrice().toString());
            result.add(nameAndPrice);
        }

        return result;

    }


    /*public List<List<String>> getDateLastnameDiscountNamesAndCountOfBooks() {

    }*/
}
