package com.example.movie;


import com.example.movie.Controller.TicketController;
import com.example.movie.Service.TicketService;
import com.example.movie.Table.Ticket;
import com.example.movie.Table.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TicketController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TicketControllerTest {

    @MockBean
    TicketService ticketService;

    @Autowired
    MockMvc mockMvc;

    Ticket ticket1, ticket2, ticket3;
    User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "s1uun", "s12345", "CUSTOMER", null);
        ticket1 = new Ticket(1, 5, LocalDate.of(2023,12,12), LocalTime.of(15, 0), 50.0, null, null);
        ticket2 = new Ticket(2, 3, LocalDate.of(2022,12,20), LocalTime.of(18, 30), 20.0, null, null);
    }

    @Test
    public void testBookTicket() throws Exception {
        Mockito.doNothing().when(ticketService).bookTicket(user.getId(),1,1);

        mockMvc.perform(get("/api/v1/ticket/book/{customer_id}/{ticket_id}", user.getId(), ticket1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testReturnTicket() throws Exception {
        Mockito.doNothing().when(ticketService).returnTicket(user.getId(), ticket1.getId(), user.getId());

        mockMvc.perform(get("/api/v1/ticket/return/{ticket_id}/{customer_id}", ticket1.getId(), user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
