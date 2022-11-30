package com.example.bookservice.components;

import com.example.bookservice.entities.Customer;
import com.example.bookservice.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class CustomerRegistrationListener {

    public static final String QUEUE_NAME = "registration-event";

    private final ObjectMapper objectMapper;
    private final CustomerService customerService;

    @RabbitListener(queues = QUEUE_NAME)
    public void listen(String message) throws Exception {
        Map<String, Object> map = (Map<String,Object>)objectMapper.readValue(message, Object.class);

        Customer customer = new Customer();
        customer.setFirstName((String)map.get("firstName"));
        customer.setLastName((String)map.get("lastName"));
        customer.setUsername((String)map.get("username"));
        customer.setUserId(Long.valueOf(((Integer)map.get("userId")).longValue()));

        customerService.save(customer);
    }
}
