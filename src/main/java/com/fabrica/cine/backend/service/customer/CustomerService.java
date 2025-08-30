package com.fabrica.cine.backend.service.customer;

import com.fabrica.cine.backend.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO register(CustomerDTO dto);
}