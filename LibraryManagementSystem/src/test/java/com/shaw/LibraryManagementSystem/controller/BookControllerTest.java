package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.dtos.BookRequest;
import com.shaw.LibraryManagementSystem.dtos.BookResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        when(bookService.getBooks(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetAllBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        List<Book> books = Collections.singletonList(book);

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/book/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddBook() throws Exception {
        BookRequest request = new BookRequest();
        request.setTitle("Test Book");
        BookResponse response = new BookResponse("Success");

        when(bookService.addBook(any(BookRequest.class))).thenReturn(response);

        mockMvc.perform(post("/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test Book\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateBook() throws Exception {
        BookRequest request = new BookRequest();
        request.setTitle("Updated Book");
        BookResponse response = new BookResponse("Success");

        when(bookService.updateBook(anyLong(), any(BookRequest.class))).thenReturn(response);

        mockMvc.perform(put("/book/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Book\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteBook() throws Exception {
        BookResponse response = new BookResponse("Success");

        when(bookService.deleteBook(anyLong())).thenReturn(response);

        mockMvc.perform(delete("/book/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success"));
    }
}