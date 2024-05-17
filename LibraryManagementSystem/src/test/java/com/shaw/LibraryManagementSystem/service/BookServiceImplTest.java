package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.dtos.BookRequest;
import com.shaw.LibraryManagementSystem.dtos.BookResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.repository.BookRepository;
import com.shaw.LibraryManagementSystem.service.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBooks(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
    }

    @Test
    void testAddBook() {
        BookRequest request = new BookRequest();
        request.setTitle("Test Book");
        request.setAuthor("Test Author");
        request.setPublishedDate(new Date());

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishedDate(request.getPublishedDate());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse response = bookService.addBook(request);
        assertEquals("Success!!!", response.getResponse());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Existing Book");
        existingBook.setAuthor("Existing Author");
        existingBook.setPublishedDate(new Date());

        BookRequest request = new BookRequest();
        request.setTitle("Updated Book");
        request.setAuthor("Updated Author");
        request.setPublishedDate(new Date());

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        BookResponse response = bookService.updateBook(1L, request);
        assertEquals("Book updated successfully!", response.getResponse());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testUpdateBookNotFound() {
        BookRequest request = new BookRequest();
        request.setTitle("Updated Book");
        request.setAuthor("Updated Author");
        request.setPublishedDate(new Date());

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookResponse response = bookService.updateBook(1L, request);
        assertEquals("Book not found!", response.getResponse());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Existing Book");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBook));
        doNothing().when(bookRepository).deleteById(anyLong());

        BookResponse response = bookService.deleteBook(1L);
        assertEquals("Book deleted successfully!", response.getResponse());
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookResponse response = bookService.deleteBook(1L);
        assertEquals("Book not found!", response.getResponse());
        verify(bookRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAllBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<Book> result = bookService.getAllBooks();
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
    }
}