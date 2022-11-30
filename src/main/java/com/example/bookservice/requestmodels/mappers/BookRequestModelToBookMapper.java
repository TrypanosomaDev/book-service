package com.example.bookservice.requestmodels.mappers;

import com.example.bookservice.entities.Book;
import com.example.bookservice.requestmodels.BookRequestModel;

public class BookRequestModelToBookMapper {

    public static void mapper(Book book, BookRequestModel bookRequestModel){
        book.setPrice(bookRequestModel.getPrice());
        book.setStock(bookRequestModel.getStock());
        book.setAuthor(bookRequestModel.getAuthor());
        book.setYear(bookRequestModel.getYear());
        book.setTitle(bookRequestModel.getTitle());
    }
}
