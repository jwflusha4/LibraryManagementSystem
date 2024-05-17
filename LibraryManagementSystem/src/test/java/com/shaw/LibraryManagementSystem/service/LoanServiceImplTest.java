package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.dtos.LoanRequest;
import com.shaw.LibraryManagementSystem.dtos.LoanResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.model.Loan;
import com.shaw.LibraryManagementSystem.model.User;
import com.shaw.LibraryManagementSystem.repository.BookRepository;
import com.shaw.LibraryManagementSystem.repository.LoanRepository;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import com.shaw.LibraryManagementSystem.service.serviceImpl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LoanServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoanBook_Success() {
        User user = new User();
        user.setId(1L);
        Book book = new Book();
        book.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(new Loan());

        LoanRequest request = new LoanRequest(1L, 1L);
        LoanResponse response = loanService.loanBook(request);

        assertEquals("Success", response.getResponse());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void testLoanBook_Failure_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        LoanRequest request = new LoanRequest(1L, 1L);
        LoanResponse response = loanService.loanBook(request);

        assertEquals("Failure", response.getResponse());
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void testLoanBook_Failure_BookNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        LoanRequest request = new LoanRequest(1L, 1L);
        LoanResponse response = loanService.loanBook(request);

        assertEquals("Failure", response.getResponse());
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void testReturnBook_Success() {
        Loan loan = new Loan();
        loan.setId(1L);

        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        doNothing().when(loanRepository).delete(any(Loan.class));

        LoanResponse response = loanService.returnBook(1L);

        assertEquals("Return Success", response.getResponse());
        verify(loanRepository, times(1)).delete(any(Loan.class));
    }

    @Test
    void testReturnBook_Failure() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.empty());

        LoanResponse response = loanService.returnBook(1L);

        assertEquals("Return Failure", response.getResponse());
        verify(loanRepository, never()).delete(any(Loan.class));
    }

    @Test
    void testGetLoansByUserId() {
        Loan loan = new Loan();
        loan.setId(1L);
        Book book = new Book();
        book.setTitle("Test Book");
        loan.setBook(book);
        loan.setReturnDate(Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(loanRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(loan));

        List<LoanResponse> responses = loanService.getLoansByUserId(1L);

        assertEquals(1, responses.size());
        assertEquals("Book: Test Book, Due Date: " + loan.getReturnDate(), responses.get(0).getResponse());
    }
}