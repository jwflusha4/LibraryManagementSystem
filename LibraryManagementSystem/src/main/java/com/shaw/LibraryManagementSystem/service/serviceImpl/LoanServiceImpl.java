package com.shaw.LibraryManagementSystem.service.serviceImpl;

import com.shaw.LibraryManagementSystem.dtos.LoanRequest;
import com.shaw.LibraryManagementSystem.dtos.LoanResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.model.Loan;
import com.shaw.LibraryManagementSystem.model.User;
import com.shaw.LibraryManagementSystem.repository.BookRepository;
import com.shaw.LibraryManagementSystem.repository.LoanRepository;
import com.shaw.LibraryManagementSystem.repository.UserRepository;
import com.shaw.LibraryManagementSystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    @Override
    public LoanResponse loanBook(LoanRequest request) {
        Optional<User> user=userRepository.findById(request.getUserId());
        Optional<Book> book=bookRepository.findById(request.getBookId());
        if (user.isPresent() && book.isPresent()) {
            LocalDate today = LocalDate.now();
            LocalDate returnDateLocal = today.plusDays(7);
            Date returnDate = Date.from(returnDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Loan loan = Loan.builder()
                    .book(book.get())
                    .user(user.get())
                    .loanDate(new Date())
                    .returnDate(returnDate)
                    .build();

            loanRepository.save(loan);

            return new LoanResponse("Success");
        } else {
            return new LoanResponse("Failure");
        }
    }

    @Override
    public LoanResponse returnBook(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            loanRepository.delete(loan.get());
            return new LoanResponse("Return Success");
        } else {
            return new LoanResponse("Return Failure");
        }
    }

    @Override
    public List<LoanResponse> getLoansByUserId(Long userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);
        return loans.stream()
                .map(loan -> new LoanResponse("Book: " + loan.getBook().getTitle() + ", Due Date: " + loan.getReturnDate()))
                .collect(Collectors.toList());
    }
}
