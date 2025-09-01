package com.fabrica.cine.backend.dto.ticket;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketConfirmationDTO {

    private String movieTitle;
    private Integer quantity;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String status;
    private LocalDateTime purchaseDate;
}
