package com.fabrica.cine.backend.service.ticket;

import com.fabrica.cine.backend.dto.ticket.TicketConfirmationDTO;
import com.fabrica.cine.backend.dto.ticket.TicketPurchaseDTO;
import com.fabrica.cine.backend.mapper.TicketPurchaseMapper;
import com.fabrica.cine.backend.model.User;
import com.fabrica.cine.backend.model.Movie;
import com.fabrica.cine.backend.model.TicketPurchase;
import com.fabrica.cine.backend.repository.CustomerRepository;
import com.fabrica.cine.backend.repository.MovieRepository;
import com.fabrica.cine.backend.repository.TicketPurchaseRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class TicketPurchaseServiceImpl {

    private final TicketPurchaseRepository ticketPurchaseRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;
    private final JavaMailSender mailSender;
    private final TicketPurchaseMapper ticketPurchaseMapper;

    public TicketPurchaseServiceImpl(TicketPurchaseRepository ticketPurchaseRepository,
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

        User customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("El cliente no existe"));

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("La película no existe"));

        if (!movie.isActive()) {
            throw new RuntimeException("La película no está activa");
        }

        if (movie.getCapacity() < dto.getQuantity()) {
            throw new RuntimeException("No hay boletas suficientes");
        }

        if (dto.getQuantity() <= 0) {
            throw new RuntimeException("La cantidad mínima de entradas a comprar es 1");
        }

        movie.setCapacity(movie.getCapacity() - dto.getQuantity());
        movieRepository.save(movie);

        TicketPurchase purchase = ticketPurchaseMapper.toEntity(dto);
        purchase.setCustomer(customer);
        purchase.setMovie(movie);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setStatus("CONFIRMADO");

        ticketPurchaseRepository.save(purchase);
        log.info("Compra con estado confirmada {} ", purchase.toString());

        sendConfirmationEmail(customer, purchase);

        return ticketPurchaseMapper.toConfirmationDto(purchase);
    }

    public List<TicketConfirmationDTO> findAll(){
        return ticketPurchaseRepository.findAll()
                .stream()
                .map(ticketPurchaseMapper::toConfirmationDto)
                .toList();
    }

    private void sendConfirmationEmail(User customer, TicketPurchase purchase) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("stiven2201@gmail.com", "TuApp");
            helper.setTo(customer.getEmail());
            helper.setSubject("Confirmación de compra de tickets");
            helper.setText(
                    "Hola " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n" +
                            "Has comprado " + purchase.getQuantity() + " tickets para la película: " +
                            purchase.getMovie().getTitle() + ".\n\n" +
                            "Estado: " + purchase.getStatus() + "\n\nGracias por tu compra!",
                    false
            );

            mailSender.send(mimeMessage);
            log.info("Correo enviado a {}", customer.getEmail());
        } catch (Exception e) {
            log.error("Error enviando correo", e);
        }
    }




}
