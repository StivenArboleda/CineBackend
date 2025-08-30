package com.fabrica.cine.backend.service.customer;

import com.fabrica.cine.backend.dto.CustomerDTO;
import com.fabrica.cine.backend.mapper.CustomerMapper;
import com.fabrica.cine.backend.model.Customer;
import com.fabrica.cine.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public CustomerDTO register(CustomerDTO dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Customer customer = customerMapper.toEntity(dto);
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));

        Customer saved = customerRepository.save(customer);

        CustomerDTO result = customerMapper.toDto(saved);
        result.setPassword(null); // nunca devolver password
        return result;
    }
}

