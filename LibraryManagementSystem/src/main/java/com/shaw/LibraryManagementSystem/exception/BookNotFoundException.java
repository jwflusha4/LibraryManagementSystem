package com.shaw.LibraryManagementSystem.exception;

public class BookNotFoundException extends RuntimeException{
    BookNotFoundException(){
        super("Book is not present!!!");
    }
}
