package com.example.bookservice.services;

import com.example.bookservice.requestmodels.OrderRequestModel;
import com.example.bookservice.responsemodels.MonthlyOrderStatisticsModel;
import com.example.bookservice.responsemodels.OrderResponseModel;

import java.util.List;

public interface OrderService {
    OrderResponseModel save(List<OrderRequestModel> order, String username);
    OrderResponseModel get(Long id, String username);
    List<OrderResponseModel> getUserOrdersByDateInterval(String username);
    void updateOrderEndDate(Long id);
    List<OrderResponseModel> getUserOrdersPaginated(String username, int pageNumber, int pageLimit);
    OrderResponseModel remove(Long id, String username);
    List<MonthlyOrderStatisticsModel> getMonthlyOrderStatistics(String username);
}
