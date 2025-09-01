package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.ticket.TicketConfirmationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fabrica.cine.backend.model.TicketPurchase;
import com.fabrica.cine.backend.dto.ticket.TicketPurchaseDTO;

@Mapper(componentModel = "spring")
public interface TicketPurchaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    TicketPurchase toEntity(TicketPurchaseDTO dto);

    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(expression = "java(purchase.getCustomer().getFirstName() + \" \" + purchase.getCustomer().getLastName())", target = "customerName")
    @Mapping(target = "customerPhone", source = "customer.phone")
    @Mapping(target = "customerEmail", source = "customer.email")
    TicketConfirmationDTO toConfirmationDto(TicketPurchase purchase);
}
