package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.dto.ticket.TicketConfirmationDTO;
import com.fabrica.cine.backend.dto.ticket.TicketPurchaseDTO;
import com.fabrica.cine.backend.service.ticket.TicketPurchaseServiceImpl;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketPurchaseController {

    private final TicketPurchaseServiceImpl ticketPurchaseService;

    public TicketPurchaseController(TicketPurchaseServiceImpl ticketPurchaseService) {
        this.ticketPurchaseService = ticketPurchaseService;
    }

    @PostMapping("/purchase")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public TicketConfirmationDTO purchase(@Valid @RequestBody TicketPurchaseDTO dto) {
        return ticketPurchaseService.purchaseTicket(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<TicketConfirmationDTO> getAllPublicMovies() {
        return ticketPurchaseService.findAll();
    }
}
