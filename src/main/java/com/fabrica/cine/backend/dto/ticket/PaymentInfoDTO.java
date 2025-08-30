package com.fabrica.cine.backend.dto.ticket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoDTO {

    private String card;
    private String month;
    private String year;
    private String code;
}
