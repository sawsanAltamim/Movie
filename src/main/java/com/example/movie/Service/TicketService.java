package com.example.movie.Service;

import com.example.movie.Api.ApiException;
import com.example.movie.Repostory.CustomerRepostory;
import com.example.movie.Repostory.MovieRepostory;
import com.example.movie.Repostory.TicketRepostory;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.Customer;
import com.example.movie.Table.Movie;
import com.example.movie.Table.Ticket;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepostory ticketRepository;
    private final MovieRepostory movieRepostory;
    private final UserRepostory userRepostory;
    private final CustomerRepostory customerRepostory;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void addTicketToMovie(Integer admin_id,Integer movie_id, Ticket ticket) {
        Movie movie = movieRepostory.findMovieById(movie_id);
        if(movie == null){
            throw new ApiException("Movie not found");
        }
        User user = userRepostory.findUserById(admin_id);
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Ticket cannot be added");
        }
        ticket.setMovie(movie);
        ticketRepository.save(ticket);
    }

    public void updateTicket(Integer admin_id ,Integer ticket_id, Ticket ticket) {
        Ticket ticket1 = ticketRepository.findTicketById(ticket_id);
        if (ticket1 == null) {
            throw new ApiException("ID not found");
        }
        User user = userRepostory.findUserById(admin_id);
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Ticket cannot be update");
        }

        ticket1.setNumberOfTicketsAvailable(ticket.getNumberOfTicketsAvailable());
        ticket1.setMovieDate(ticket.getMovieDate());
        ticket1.setMovieTime(ticket.getMovieTime());
        ticket1.setTicketCost(ticket.getTicketCost());
        ticketRepository.save(ticket1);

    }
    public void deleteTicket(Integer admin_id ,Integer ticket_id){
        Ticket ticket = ticketRepository.findTicketById(ticket_id);
        if(ticket == null){
            throw new ApiException("ID not found");
        }
        User user = userRepostory.findUserById(admin_id);
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Ticket cannot be update");
        }
        ticketRepository.delete(ticket);
    }

    public void MovieDateValid(Integer ticket_id) {
        Ticket ticket = ticketRepository.findTicketById(ticket_id);

        if (ticket == null) {
            throw new ApiException("Ticket not found");
        }

        LocalDate movieDate = ticket.getMovieDate();
        LocalTime movieTime = ticket.getMovieTime();
        LocalDateTime movieDateTime = movieDate.atTime(movieTime);

        LocalDateTime now = LocalDateTime.now();
        if (movieDateTime.isBefore(now)){ // Check if the date and time of the movie is before the now date
            throw new ApiException("Movie date is not valid");
        }
    }

    public void bookTicket(Integer user_id, Integer customer_id ,Integer ticket_id) {
        Ticket ticket = ticketRepository.findTicketById(ticket_id);
        if (ticket == null) {
            throw new ApiException("Ticket not found");
        }

        User user = userRepostory.findUserById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Customer customer = customerRepostory.findCustomerById(customer_id);
        if (!(user.getId()==customer.getId())){
            throw new ApiException("Verify the ID");
        }

        // Check if tickets are available
        if (ticket.getNumberOfTicketsAvailable() <= 0) {
            throw new ApiException("No available tickets");
        }

        if (customer.getBalance() < ticket.getTicketCost()) {
            throw new ApiException("Insufficient balance");
        }

        double ticketCost = ticket.getTicketCost();
        boolean hasDiscountCoupon = customer.getDiscountCoupon();

        if (hasDiscountCoupon == true) {
            ticketCost *= 0.9;
            customer.setBalance(customer.getBalance() - ticketCost);
            customer.setDiscountCoupon(false);
            customerRepostory.save(customer);
        } else {
            customer.setBalance(customer.getBalance() - ticket.getTicketCost());
            customerRepostory.save(customer);
        }
        ticket.setNumberOfTicketsAvailable(ticket.getNumberOfTicketsAvailable() - 1);
        ticketRepository.save(ticket);

        customer.getTicketSet().add(ticket);
        customerRepostory.save(customer);

    }

    public void returnTicket(Integer user_id ,Integer ticket_id, Integer customer_id){
        Ticket ticket = ticketRepository.findTicketById(ticket_id);
        if(ticket == null){
            throw new ApiException("Ticket not found");
        }

        User user = userRepostory.findUserById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Customer customer = customerRepostory.findCustomerById(customer_id);
        if (!(user.getId()==customer.getId())){
            throw new ApiException("Verify the ID");
        }

        MovieDateValid(ticket.getId()); //Checks if the ticket is valid for use or not

        double ticketCost = ticket.getTicketCost();
        boolean hasDiscountCoupon = customer.getDiscountCoupon();

        if (hasDiscountCoupon == true) {
            ticketCost *= 0.9;
            customer.setBalance(customer.getBalance() + ticketCost);
            customerRepostory.save(customer);
        } else {
            customer.setBalance(customer.getBalance() + ticket.getTicketCost());
            customerRepostory.save(customer);
        }
        ticket.setNumberOfTicketsAvailable(ticket.getNumberOfTicketsAvailable() + 1);
        ticketRepository.save(ticket);

    }
}
