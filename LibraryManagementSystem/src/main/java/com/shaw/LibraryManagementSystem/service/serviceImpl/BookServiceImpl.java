package com.shaw.LibraryManagementSystem.service.serviceImpl;

import com.shaw.LibraryManagementSystem.dtos.BookRequest;
import com.shaw.LibraryManagementSystem.dtos.BookResponse;
import com.shaw.LibraryManagementSystem.model.Book;
import com.shaw.LibraryManagementSystem.repository.BookRepository;
import com.shaw.LibraryManagementSystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> getBooks(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public BookResponse addBook(BookRequest request) {
        Book book=Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publishedDate(request.getPublishedDate())
                .build();
        bookRepository.save(book);
        BookResponse bookResponse=BookResponse.builder()
                .response("Success!!!")
                .build();
        return bookResponse;
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPublishedDate(request.getPublishedDate());
            bookRepository.save(book);
            return BookResponse.builder()
                    .response("Book updated successfully!")
                    .build();
        } else {
            return BookResponse.builder()
                    .response("Book not found!")
                    .build();
        }
    }

    @Override
    public BookResponse deleteBook(Long id) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            bookRepository.deleteById(id);
            return BookResponse.builder()
                    .response("Book deleted successfully!")
                    .build();
        } else {
            return BookResponse.builder()
                    .response("Book not found!")
                    .build();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> result= bookRepository.findAll();
        return result;
    }
}
