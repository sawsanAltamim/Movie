package com.example.movie.Repostory;

import com.example.movie.Table.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepostory extends JpaRepository<Ticket, Integer> {

    Ticket findTicketById(Integer id);
}
