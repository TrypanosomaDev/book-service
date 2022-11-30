package com.example.bookservice.services.impl;

import com.example.bookservice.entities.*;
import com.example.bookservice.repositories.BookRepository;
import com.example.bookservice.repositories.CustomerRepository;
import com.example.bookservice.repositories.OrderRepository;
import com.example.bookservice.requestmodels.OrderRequestModel;
import com.example.bookservice.responsemodels.MonthlyOrderStatisticsModel;
import com.example.bookservice.responsemodels.OrderResponseModel;
import com.example.bookservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderResponseModel save(List<OrderRequestModel> order, String username) {
        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("customer not found"));

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        orderRepository.save(newOrder);

        for(OrderRequestModel bookOrderPair : order) {
            Long bookId = bookOrderPair.getBookId();
            Integer number = bookOrderPair.getNumber();

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("book not found"));

            if(book.getStock() - number >= 0) {
                book.setStock(book.getStock() - number);
            }
            else {
                throw new IllegalStateException(String.format("Book %s stock is not enough for the order",
                        book.getTitle()));
            }
            newOrder.addBook(book, number);
            bookRepository.save(book);
        }

        orderRepository.save(newOrder);

        return new OrderResponseModel(newOrder.getCustomer(), newOrder);
    }

    @Transactional
    public OrderResponseModel remove(Long id, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order not found"));

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("customer not found"));

        if(!order.getCustomer().equals(customer)){
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN,
                    "Order is not belong to current user. Order can be only deleted by the owner of the order");
        }
        OrderResponseModel orderResponseModel = new OrderResponseModel(order.getCustomer(), order);

        for(BookOrder bookOrder : order.getBooks()) {
            Integer number = bookOrder.getNumber();
            Book book = bookOrder.getBook();
            book.setStock(book.getStock() + number);
            bookRepository.save(book);
        }

        orderRepository.delete(order);

        return orderResponseModel;
    }

    public OrderResponseModel get(Long id, String username){
        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("customer not found"));

        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("order not found"));

        if(!order.getCustomer().equals(customer)){
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Can not access other user's order");
        }

        return new OrderResponseModel(customer, order);
    }

    public List<OrderResponseModel> getUserOrdersPaginated(String username, int pageNumber, int pageLimit){

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("customer not found"));

        List<Order> orders = orderRepository.findAllByCustomer(customer,
                PageRequest.of(pageNumber - 1, pageLimit));

        List<OrderResponseModel> list = new ArrayList<>();
        orders.forEach(order -> list.add(new OrderResponseModel(customer, order)));

        return list;
    }

    public List<OrderResponseModel> getUserOrdersByDateInterval(String username){

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("customer not found"));

        List<Order> orders = orderRepository.findOrdersByCustomerAndOrderByDateInterval(customer.getId())
                .orElseThrow(() -> new EntityNotFoundException("order not found"));


        List<OrderResponseModel> list = new ArrayList<>();
        orders.forEach(order -> list.add(new OrderResponseModel(customer, order)));

        return list;
    }

    public void updateOrderEndDate(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("order not found"));

        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setEndDate(LocalDateTime.now());
        orderRepository.save(order);
    }

    public List<MonthlyOrderStatisticsModel> getMonthlyOrderStatistics(String username){
        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("customer not found"));

        return orderRepository.getMonthlyStatisticsOfCustomerOrder(customer.getId());
    }

}
