package com.fabrica.cine.backend.dto.ticket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketPurchaseDTO {

    private Long customerId;
    private Long movieId;
    private Integer quantity;
    private PaymentInfoDTO paymentInfo;
}
