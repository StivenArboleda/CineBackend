package com.fabrica.cine.backend.service.ticket;

import com.fabrica.cine.backend.model.TicketPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, Long> {
}
