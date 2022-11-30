package com.example.bookservice.controllers;

import com.example.bookservice.Constants.UserRoles;
import com.example.bookservice.requestmodels.OrderRequestModel;
import com.example.bookservice.responsemodels.MonthlyOrderStatisticsModel;
import com.example.bookservice.responsemodels.OrderResponseModel;
import com.example.bookservice.services.AuthService;
import com.example.bookservice.services.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<OrderResponseModel> add(@RequestHeader("Authorization") String token,
                                                 @RequestBody List<OrderRequestModel> order)
            throws JsonProcessingException {

        String username = authService.getUsername(token, UserRoles.USER);
        OrderResponseModel orderResponseModel = orderService.save(order, username);
        return ResponseEntity.ok().body(orderResponseModel);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponseModel> remove(@RequestHeader("Authorization") String token,
                       @PathVariable("orderId") Long orderId)
            throws JsonProcessingException {

        String username = authService.getUsername(token, UserRoles.USER);
        OrderResponseModel orderResponseModel = orderService.remove(orderId, username);
        return ResponseEntity.ok().body(orderResponseModel);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseModel> get(@RequestHeader("Authorization") String token,
                                                 @PathVariable("orderId") Long orderId)
            throws JsonProcessingException {

        String username = authService.getUsername(token, UserRoles.USER);
        OrderResponseModel orderResponseModel = orderService.get(orderId, username);
        return ResponseEntity.ok().body(orderResponseModel);
    }


    @GetMapping("/orderByDateInterval")
    public ResponseEntity<List<OrderResponseModel>> getOrdersByDateInterval
            (@RequestHeader("Authorization") String token) throws JsonProcessingException {

        String username = authService.getUsername(token, UserRoles.USER);
        List<OrderResponseModel> list = orderService.getUserOrdersByDateInterval(username);
        return ResponseEntity.ok().body(list);
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseModel>> getOrders
            (@RequestHeader("Authorization") String token,
             @RequestParam(defaultValue = "1")  Integer pageNumber,
             @RequestParam(defaultValue = "10")  Integer pageLimit)
            throws JsonProcessingException {

        String username = authService.getUsername(token, UserRoles.USER);
        List<OrderResponseModel> list = orderService.getUserOrdersPaginated(username, pageNumber, pageLimit);
        return ResponseEntity.ok().body(list);
    }


    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<String> updateOrderEndDate
            (@RequestHeader("Authorization") String token,
             @PathVariable("orderId") Long orderId) {

        authService.hasPermission(token, UserRoles.ADMIN);
        orderService.updateOrderEndDate(orderId);
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/monthlyStatistics")
    public ResponseEntity<List<MonthlyOrderStatisticsModel>> getStatisticsByCustomer
            (@RequestHeader("Authorization") String token) throws JsonProcessingException {

        String username = authService.getUsername(token, UserRoles.USER);
        List<MonthlyOrderStatisticsModel> list  = orderService.getMonthlyOrderStatistics(username);
        return ResponseEntity.ok().body(list);
    }

}
