package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.controller.LoanController;
import com.shaw.LibraryManagementSystem.dtos.LoanRequest;
import com.shaw.LibraryManagementSystem.dtos.LoanResponse;
import com.shaw.LibraryManagementSystem.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoanBook() {
        LoanRequest request = new LoanRequest(1L, 1L);
        LoanResponse expectedResponse = new LoanResponse("Success");
        when(loanService.loanBook(request)).thenReturn(expectedResponse);

        LoanResponse actualResponse = loanController.loanBook(request);

        assertEquals(expectedResponse, actualResponse);
        verify(loanService, times(1)).loanBook(request);
    }

    @Test
    public void testReturnBook() {
        Long loanId = 1L;
        LoanResponse expectedResponse = new LoanResponse("Success");
        when(loanService.returnBook(loanId)).thenReturn(expectedResponse);

        LoanResponse actualResponse = loanController.returnBook(loanId);

        assertEquals(expectedResponse, actualResponse);
        verify(loanService, times(1)).returnBook(loanId);
    }

    @Test
    public void testGetLoansByUserId() {
        Long userId = 1L;
        List<LoanResponse> expectedResponses = new ArrayList<>();
        // Populate expectedResponses with test data

        when(loanService.getLoansByUserId(userId)).thenReturn(expectedResponses);

        List<LoanResponse> actualResponses = loanController.getLoansByUserId(userId);

        assertEquals(expectedResponses, actualResponses);
        verify(loanService, times(1)).getLoansByUserId(userId);
    }
}