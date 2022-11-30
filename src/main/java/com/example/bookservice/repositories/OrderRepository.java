package com.example.bookservice.repositories;

import com.example.bookservice.entities.Customer;
import com.example.bookservice.entities.Order;
import com.example.bookservice.responsemodels.MonthlyOrderStatisticsModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    List<Order> findAllByCustomer(Customer customer, Pageable pageable);

    @Query(value =
            "select o.id as id, o.customer_id as customer_id, o.end_date as end_date, \n" +
                    " o.start_date as start_date, o.order_status as order_status, \n" +
                "CASE WHEN end_date is null THEN NOW() - start_date ELSE end_date - start_date END \n" +
                "AS difference \n" +
            "FROM order_table as o \n" +
            "WHERE o.customer_id=?1 \n" +
            "ORDER BY difference", nativeQuery = true)
    Optional<List<Order>> findOrdersByCustomerAndOrderByDateInterval(Long customerId);



    @Query(value =
    "select to_char(date_trunc('month', o.start_date), 'mon') AS monthName, sum(bo.number) as totalBookCount, \n" +
    "count(distinct o.id) as totalOrderCount, sum(b.price * bo.number) as totalPurchasedAmount\n" +
    "from order_table o \n" +
    "INNER JOIN book_order bo on o.id = bo.order_id \n" +
    "INNER JOIN book b on bo.book_id = b.id \n" +
    "WHERE o.customer_id=?1 \n" +
    "GROUP BY monthName", nativeQuery = true)
    List<MonthlyOrderStatisticsModel> getMonthlyStatisticsOfCustomerOrder(Long customerId);
}
