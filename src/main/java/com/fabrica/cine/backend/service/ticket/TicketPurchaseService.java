package com.fabrica.cine.backend.service.ticket;

import com.fabrica.cine.backend.dto.ticket.TicketConfirmationDTO;
import com.fabrica.cine.backend.dto.ticket.TicketPurchaseDTO;
import com.fabrica.cine.backend.mapper.TicketPurchaseMapper;
import com.fabrica.cine.backend.model.Customer;
import com.fabrica.cine.backend.model.Movie;
import com.fabrica.cine.backend.model.TicketPurchase;
import com.fabrica.cine.backend.repository.CustomerRepository;
import com.fabrica.cine.backend.repository.MovieRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketPurchaseService {

    private final TicketPurchaseRepository ticketPurchaseRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;
    private final JavaMailSender mailSender;
    private final TicketPurchaseMapper ticketPurchaseMapper;

    public TicketPurchaseService(TicketPurchaseRepository ticketPurchaseRepository,
                                 MovieRepository movieRepository,
                                 CustomerRepository customerRepository,
                                 JavaMailSender mailSender,
                                 TicketPurchaseMapper ticketPurchaseMapper) {
        this.ticketPurchaseRepository = ticketPurchaseRepository;
        this.movieRepository = movieRepository;
        this.customerRepository = customerRepository;
        this.mailSender = mailSender;
        this.ticketPurchaseMapper = ticketPurchaseMapper;
    }

    public TicketConfirmationDTO purchaseTicket(TicketPurchaseDTO dto) {
        // Validación de Customer
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("El cliente no existe"));

        // Validación de Movie
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("La película no existe"));

        if (!movie.isActive()) {
            throw new RuntimeException("La película no está activa");
        }

        if (movie.getCapacity() < dto.getQuantity()) {
            throw new RuntimeException("No hay boletas suficientes");
        }

        // Restar capacidad
        movie.setCapacity(movie.getCapacity() - dto.getQuantity());
        movieRepository.save(movie);

        // Mapear DTO -> Entity
        TicketPurchase purchase = ticketPurchaseMapper.toEntity(dto);
        purchase.setCustomer(customer);
        purchase.setMovie(movie);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setStatus("CONFIRMADO");

        // Guardar
        ticketPurchaseRepository.save(purchase);

        // Notificar por correo
        sendConfirmationEmail(customer, purchase);

        // Entity -> ConfirmationDTO
        return ticketPurchaseMapper.toConfirmationDto(purchase);
    }

    private void sendConfirmationEmail(Customer customer, TicketPurchase purchase) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hello@demomailtrap.co");
        message.setTo(customer.getEmail());
        message.setSubject("Confirmación de compra de tickets");
        message.setText("Hola " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n" +
                "Has comprado " + purchase.getQuantity() + " tickets para la película: " +
                purchase.getMovie().getTitle() + ".\n\n" +
                "Estado: " + purchase.getStatus() + "\n\nGracias por tu compra!");
        mailSender.send(message);
    }


}
