package com.example.bookservice.services.impl;

import com.example.bookservice.entities.Customer;
import com.example.bookservice.repositories.CustomerRepository;
import com.example.bookservice.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public void save(Customer customer){
        customerRepository.save(customer);

    }

}
