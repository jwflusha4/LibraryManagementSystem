package com.shaw.LibraryManagementSystem.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User is not found");
    }
}
