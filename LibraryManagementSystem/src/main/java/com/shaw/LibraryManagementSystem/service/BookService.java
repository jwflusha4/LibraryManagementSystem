package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.dtos.BookRequest;
import com.shaw.LibraryManagementSystem.dtos.BookResponse;
import com.shaw.LibraryManagementSystem.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getBooks(Long id);

    BookResponse addBook(BookRequest request);

    BookResponse updateBook(Long id, BookRequest request);

    BookResponse deleteBook(Long id);

    List<Book> getAllBooks();

}
