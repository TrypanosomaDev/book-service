package com.example.bookservice.services.impl;

import com.example.bookservice.entities.Book;
import com.example.bookservice.repositories.BookRepository;
import com.example.bookservice.requestmodels.BookRequestModel;
import com.example.bookservice.requestmodels.mappers.BookRequestModelToBookMapper;
import com.example.bookservice.responsemodels.BookResponseModel;
import com.example.bookservice.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookResponseModel get(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        return new BookResponseModel(book);
    }
    public List<BookResponseModel> getAll(){
        List<Book> books = bookRepository.findAll();
        List<BookResponseModel> list = new ArrayList<>();
        books.forEach(b -> list.add(new BookResponseModel(b)));
        return list;
    }
    public BookResponseModel update(BookRequestModel bookRequestModel, Long bookId){

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));

        BookRequestModelToBookMapper.mapper(book, bookRequestModel);
        Book result = bookRepository.save(book);
        return new BookResponseModel(result);
    }

    public BookResponseModel save(BookRequestModel bookRequestModel){
        Book book = new Book();
        BookRequestModelToBookMapper.mapper(book, bookRequestModel);
        Book result = bookRepository.save(book);
        return new BookResponseModel(result);
    }

}
