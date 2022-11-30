package com.example.bookservice.controllers;

import com.example.bookservice.Constants.UserRoles;
import com.example.bookservice.requestmodels.BookRequestModel;
import com.example.bookservice.responsemodels.BookResponseModel;
import com.example.bookservice.services.AuthService;
import com.example.bookservice.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/books")
public class BookController {

    private final BookService bookService;
    private final AuthService authService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseModel> get(@RequestHeader("Authorization") String token,
                                                 @PathVariable("bookId") Long bookId) {

        authService.hasPermission(token, UserRoles.NOT_IMPORTANT);
        BookResponseModel bookResponseModel = bookService.get(bookId);
        return ResponseEntity.ok().body(bookResponseModel);
    }
    @GetMapping()
    public ResponseEntity<List<BookResponseModel>> get(@RequestHeader("Authorization") String token) {

        authService.hasPermission(token, UserRoles.NOT_IMPORTANT);
        List<BookResponseModel> list = bookService.getAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookResponseModel> update(@RequestHeader("Authorization") String token,
                                                    @RequestBody @Valid BookRequestModel bookRequestModel,
                                                    @PathVariable("bookId") Long bookId) {

        authService.hasPermission(token, UserRoles.ADMIN);
        BookResponseModel bookResponseModel = bookService.update(bookRequestModel, bookId);
        return ResponseEntity.ok().body(bookResponseModel);
    }

    @PostMapping
    public ResponseEntity<BookResponseModel> add(@RequestHeader("Authorization") String token,
                                                 @RequestBody @Valid BookRequestModel bookRequestModel) {

        authService.hasPermission(token, UserRoles.ADMIN);
        BookResponseModel bookResponseModel = bookService.save(bookRequestModel);
        return ResponseEntity.ok().body(bookResponseModel);
    }
}
