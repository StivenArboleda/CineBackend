package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.ticket.TicketConfirmationDTO;
import com.fabrica.cine.backend.dto.ticket.TicketPurchaseDTO;
import com.fabrica.cine.backend.service.ticket.TicketPurchaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketPurchaseController {

    private final TicketPurchaseService ticketPurchaseService;

    public TicketPurchaseController(TicketPurchaseService ticketPurchaseService) {
        this.ticketPurchaseService = ticketPurchaseService;
    }

    @PostMapping("/purchase")
    public TicketConfirmationDTO purchase(@Valid @RequestBody TicketPurchaseDTO dto) {
        return ticketPurchaseService.purchaseTicket(dto);
    }
}
