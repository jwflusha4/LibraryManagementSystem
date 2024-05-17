package com.shaw.LibraryManagementSystem.service;

import com.shaw.LibraryManagementSystem.dtos.LoanRequest;
import com.shaw.LibraryManagementSystem.dtos.LoanResponse;

import java.util.List;

public interface LoanService {
    LoanResponse loanBook(LoanRequest request);

    LoanResponse returnBook(Long loanId);

    List<LoanResponse> getLoansByUserId(Long userId);

}
