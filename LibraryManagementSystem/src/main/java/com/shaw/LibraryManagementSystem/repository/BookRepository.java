package com.shaw.LibraryManagementSystem.repository;

import com.shaw.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

}
