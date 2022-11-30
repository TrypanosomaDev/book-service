package com.example.bookservice.services;

import com.example.bookservice.requestmodels.BookRequestModel;
import com.example.bookservice.responsemodels.BookResponseModel;

import java.util.List;

public interface BookService {
    BookResponseModel get(Long id);
    BookResponseModel update(BookRequestModel bookRequestModel, Long bookId);
    BookResponseModel save(BookRequestModel bookRequestModel);
    List<BookResponseModel> getAll();
}
