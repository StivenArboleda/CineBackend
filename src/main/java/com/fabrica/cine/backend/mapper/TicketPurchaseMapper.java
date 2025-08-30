package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.ticket.TicketConfirmationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fabrica.cine.backend.model.TicketPurchase;
import com.fabrica.cine.backend.dto.ticket.TicketPurchaseDTO;

@Mapper(componentModel = "spring")
public interface TicketPurchaseMapper {

    // De DTO de entrada a entidad (cuando llega la petición)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true) // se setea en el service
    @Mapping(target = "movie", ignore = true)    // se setea en el service
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    TicketPurchase toEntity(TicketPurchaseDTO dto);

    // De entidad a DTO de confirmación (cuando devuelves la respuesta)
    @Mapping(source = "movie.title", target = "movieTitle")
    @Mapping(expression = "java(purchase.getCustomer().getFirstName() + \" \" + purchase.getCustomer().getLastName())", target = "customerName")
    TicketConfirmationDTO toConfirmationDto(TicketPurchase purchase);
}
