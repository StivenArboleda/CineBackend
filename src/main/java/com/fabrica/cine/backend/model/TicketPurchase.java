package com.fabrica.cine.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ticket_purchase")
public class TicketPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull(message = "El cliente es obligatorio")
    private User customer;

    @ManyToOne(optional = false)
    @NotNull(message = "La película es obligatoria")
    private Movie movie;

    @NotNull(message = "La cantidad de tickets es obligatoria")
    @Min(value = 1, message = "La cantidad mínima de tickets es 1")
    private Integer quantity;

    @Embedded
    private PaymentInfo paymentInfo;

    @PastOrPresent(message = "La fecha de compra no puede estar en el futuro")
    private LocalDateTime purchaseDate;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "PENDIENTE|CONFIRMADO|CANCELADO",
            message = "El estado debe ser PENDIENTE, CONFIRMADO o CANCELADO"
    )
    private String status;
}
