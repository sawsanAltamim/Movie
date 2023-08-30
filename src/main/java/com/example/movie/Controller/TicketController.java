package com.example.movie.Controller;

import com.example.movie.Api.ApiResponse;
import com.example.movie.Service.TicketService;
import com.example.movie.Table.Ticket;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/get")
    public ResponseEntity getAllTickets(){
       return ResponseEntity.status(HttpStatus.OK).body(ticketService.getAllTickets());
    }

    @PostMapping("/add/{movie_id}")
    public ResponseEntity addTicket(@AuthenticationPrincipal User user ,@PathVariable Integer movie_id,@RequestBody Ticket ticket) {
        ticketService.addTicketToMovie(user.getId(),movie_id, ticket);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ticket added"));
    }
    @PutMapping("/update/{ticketId}")
    public ResponseEntity updateTicket(@AuthenticationPrincipal User user,@PathVariable Integer ticketId, @RequestBody Ticket updatedTicket) {
        ticketService.updateTicket(user.getId(),ticketId, updatedTicket);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ticket updated"));
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity deleteTicket(@AuthenticationPrincipal User user,@PathVariable Integer ticketId) {
        ticketService.deleteTicket(user.getId(), ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ticket deleted"));
    }

    @GetMapping("/book/{customer_id}/{ticket_id}")
    public ResponseEntity bookTicket(@AuthenticationPrincipal User user , @PathVariable Integer customer_id , @PathVariable Integer ticket_id ) {
        ticketService.bookTicket(user.getId(),customer_id,ticket_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Ticket booked successfully"));
    }
}
