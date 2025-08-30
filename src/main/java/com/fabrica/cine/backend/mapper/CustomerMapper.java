package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.CustomerDTO;
import com.fabrica.cine.backend.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDto(Customer customer);
    Customer toEntity(CustomerDTO dto);
}
