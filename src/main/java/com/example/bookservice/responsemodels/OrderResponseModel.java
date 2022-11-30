package com.example.bookservice.responsemodels;
import com.example.bookservice.entities.BookOrder;
import com.example.bookservice.entities.Customer;
import com.example.bookservice.entities.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponseModel {
    private Long id; // orderid
    private String customerUsername; // customer username
    private Set<BookOrderModel> books;
    private Double totalPrice; // total price of the order

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime endDate;


    public OrderResponseModel(Customer customer, Order order) {
        this.setId(order.getId());
        this.setCustomerUsername(customer.getUsername());

        Set<BookOrderModel> bookOrderSet = new HashSet<>();

        Double totalOrderCost = 0.0;
        for(BookOrder bookOrder : order.getBooks()){
            BookOrderModel bookOrderModel = new BookOrderModel(bookOrder);
            bookOrderSet.add(bookOrderModel);
            totalOrderCost += bookOrderModel.getTotalCost();
        }
        this.setBooks(bookOrderSet);
        this.setTotalPrice(totalOrderCost);
        this.setStartDate(order.getStartDate());
        this.setEndDate(order.getEndDate());
    }
}
