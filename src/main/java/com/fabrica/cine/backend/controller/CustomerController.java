package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.CustomerDTO;
import com.fabrica.cine.backend.service.customer.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @PostMapping("/register")
    public CustomerDTO register(@Valid @RequestBody CustomerDTO customerDTO) {
        return customerServiceImpl.register(customerDTO);
    }
}
