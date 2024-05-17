package com.shaw.LibraryManagementSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
    private String title;
    private String author;
    private Date publishedDate;
}
