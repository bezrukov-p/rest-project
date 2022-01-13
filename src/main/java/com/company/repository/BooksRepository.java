package com.company.repository;

import com.company.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT name FROM books GROUP BY name", nativeQuery = true)
    List<String> getDifferentBookTitles();

    @Query(value = "SELECT price FROM books GROUP BY price", nativeQuery = true)
    List<Double> getDifferentBookPrices();

    List<Book> getBooksByNameContainingOrPriceGreaterThan(String name, Double price);

}
