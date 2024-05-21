package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.controller.BookController;
import com.shaw.LibraryManagementSystem.dtos.BookRequest;
import com.shaw.LibraryManagementSystem.dtos.BookResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBook() {
        Long bookId = 1L;
        Book expectedBook = new Book(); // Create a sample book
        when(bookService.getBooks(bookId)).thenReturn(Optional.of(expectedBook));

        Optional<Book> actualBook = bookController.getBook(bookId);

        assertTrue(actualBook.isPresent());
        assertEquals(expectedBook, actualBook.get());
        verify(bookService, times(1)).getBooks(bookId);
    }

    @Test
    public void testGetAllBook() {
        List<Book> expectedBooks = new ArrayList<>(); // Populate with sample books
        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookController.getAllBook();

        assertEquals(expectedBooks, actualBooks);
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testAddBook() {
        BookRequest request = new BookRequest(); // Create a sample book request
        BookResponse expectedResponse = new BookResponse("Book added successfully");
        when(bookService.addBook(request)).thenReturn(expectedResponse);

        BookResponse actualResponse = bookController.addBook(request);

        assertEquals(expectedResponse, actualResponse);
        verify(bookService, times(1)).addBook(request);
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        BookRequest request = new BookRequest(); // Create a sample book request
        BookResponse expectedResponse = new BookResponse("Book updated successfully");
        when(bookService.updateBook(bookId, request)).thenReturn(expectedResponse);

        BookResponse actualResponse = bookController.updateBook(bookId, request);

        assertEquals(expectedResponse, actualResponse);
        verify(bookService, times(1)).updateBook(bookId, request);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        BookResponse expectedResponse = new BookResponse("Book deleted successfully");
        when(bookService.deleteBook(bookId)).thenReturn(expectedResponse);

        BookResponse actualResponse = bookController.deleteBook(bookId);

        assertEquals(expectedResponse, actualResponse);
        verify(bookService, times(1)).deleteBook(bookId);
    }
}