package com.company.controller;

import com.company.exception.ResourceNotFoundException;
import com.company.model.Book;
import com.company.repository.BooksRepository;
import com.company.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class BooksController {

    @Autowired
    BooksRepository booksRepository;
    @Autowired
    MainService mainService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {

        Book book = booksRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("book not found for id: " + id)
        );

        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book){
        return booksRepository.save(book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        booksRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("book not found for id: " + id));
        booksRepository.deleteById(id);

        return ResponseEntity.ok("deleted");
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Integer id,
                                           @RequestBody Book bookDetails) throws ResourceNotFoundException {
        Book book = booksRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("book not found for id: " + id));

        book.setName(bookDetails.getName());
        book.setNumber(bookDetails.getNumber());
        book.setStock(bookDetails.getStock());
        book.setPrice(bookDetails.getPrice());

        final Book updatedBook = booksRepository.save(book);

        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<Book> updateBookPartially(@PathVariable(value = "id") Integer id,
                                                    @RequestBody Book bookDetails) throws ResourceNotFoundException {
        Book book = booksRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "book not found for id: \" + id"));

        if (bookDetails.getName() != null)
            book.setName(bookDetails.getName());
        if (bookDetails.getNumber() != null)
            book.setNumber((bookDetails.getNumber()));
        if (bookDetails.getPrice() != null)
            book.setPrice(bookDetails.getPrice());
        if (bookDetails.getStock() != null)
            book.setPrice(bookDetails.getPrice());

        final Book bookUpdated = booksRepository.save(book);

        return ResponseEntity.ok(bookUpdated);
    }




    @GetMapping("/books/different-names-and-prices")
    public List<String> getDifferentNamesAndPrices(){
        return mainService.getBooksDifferentNamesAndPrice();
    }

    @GetMapping("/books/by-string-or-price-greater-than")
    public List<List<String>> getNamesAndPricesOfBooksByNameContainsOrPriceGreaterThan(@RequestParam String string,
                                                                       @RequestParam Double price) {

        return mainService.getNamesAndPricesOfBooksByNameContainsOrPriceGreaterThan(string, price);
    }





}
