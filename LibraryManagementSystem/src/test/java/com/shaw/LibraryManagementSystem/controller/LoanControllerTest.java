package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.dtos.LoanRequest;
import com.shaw.LibraryManagementSystem.dtos.LoanResponse;
import com.shaw.LibraryManagementSystem.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class LoanControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    void testLoanBook() throws Exception {
        LoanRequest request = new LoanRequest();
        LoanResponse response = new LoanResponse("Success");

        when(loanService.loanBook(any(LoanRequest.class))).thenReturn(response);

        mockMvc.perform(post("/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"bookId\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Success"));
    }

    @Test
    void testReturnBook() throws Exception {
        LoanResponse response = new LoanResponse("Success");

        when(loanService.returnBook(anyLong())).thenReturn(response);

        mockMvc.perform(post("/loan/return/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Success"));
    }

    @Test
    void testGetLoansByUserId() throws Exception {
        LoanResponse response = new LoanResponse("Success");
        List<LoanResponse> responses = Collections.singletonList(response);

        when(loanService.getLoansByUserId(anyLong())).thenReturn(responses);

        mockMvc.perform(get("/loan/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Success"));
    }
}