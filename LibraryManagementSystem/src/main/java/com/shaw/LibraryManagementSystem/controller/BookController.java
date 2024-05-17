package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.dtos.BookRequest;
import com.shaw.LibraryManagementSystem.dtos.BookResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<Book> getBook(@PathVariable("id") Long id) {
        return bookService.getBooks(id);
    }
    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Book> getAllBook() {
        return bookService.getAllBooks();
    }

    @PostMapping("/add")
    //@PreAuthorize("hasRole('ADMIN')")
    public BookResponse addBook(@RequestBody BookRequest request) {
        return bookService.addBook(request);
    }

    @PutMapping("/update/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public BookResponse updateBook(@PathVariable("id") Long id, @RequestBody BookRequest request) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public BookResponse deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

}
