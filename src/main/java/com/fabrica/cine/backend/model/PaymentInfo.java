package com.fabrica.cine.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PaymentInfo {
    private String card;
    @Column(name = "exp_month")
    private String month;
    @Column(name = "exp_year")
    private String year;
    private String code;
}