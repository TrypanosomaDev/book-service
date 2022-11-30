package com.example.bookservice.responsemodels;

import com.example.bookservice.entities.Book;
import lombok.Data;

@Data
public class BookResponseModel {
    private Long id;
    private Integer year;
    private String title;
    private Double price;
    private String author;
    private Integer stock;

    public BookResponseModel(Book book) {
        this.id = book.getId();
        this.year = book.getYear();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.author = book.getAuthor();
        this.stock = book.getStock();
    }
}


