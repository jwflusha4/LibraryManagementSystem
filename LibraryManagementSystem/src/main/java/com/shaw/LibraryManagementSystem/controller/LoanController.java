package com.shaw.LibraryManagementSystem.controller;

import com.shaw.LibraryManagementSystem.dtos.LoanRequest;
import com.shaw.LibraryManagementSystem.dtos.LoanResponse;
import com.shaw.LibraryManagementSystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public LoanResponse loanBook(@RequestBody LoanRequest request){
        return loanService.loanBook(request);
    }

    @PostMapping("/return/{loanId}")
    public LoanResponse returnBook(@PathVariable Long loanId) {
        LoanResponse response = loanService.returnBook(loanId);
        return response;
    }

    @GetMapping("/{userId}")
    public List<LoanResponse> getLoansByUserId(@PathVariable Long userId) {
        List<LoanResponse> responses = loanService.getLoansByUserId(userId);
        return responses;
    }
}
