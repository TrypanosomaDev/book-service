package com.example.bookservice.responsemodels;

import com.example.bookservice.entities.BookOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookOrderModel {
    private Long bookId;
    private String bookTitle;
    private Integer number;
    private Double unitCost;
    private Double totalCost;

    public BookOrderModel(BookOrder bookOrder) {
        this.bookId = bookOrder.getBook().getId();
        this.bookTitle = bookOrder.getBook().getTitle();
        this.number = bookOrder.getNumber();
        this.unitCost = bookOrder.getBook().getPrice();
        this.totalCost = bookOrder.getBook().getPrice() * bookOrder.getNumber();
    }
}
