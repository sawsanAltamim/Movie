package com.example.movie.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    private Integer numberOfTicketsAvailable;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    //@PastOrPresent
    private LocalDate movieDate;

    @NotNull
    @JsonFormat(pattern = "hh:mm:ss a")
    private LocalTime movieTime;

    @NotNull
    @PositiveOrZero
    private Double ticketCost;

    @ManyToOne
    @JoinColumn(name = "movie_ticket_id", referencedColumnName = "id")
    @JsonIgnore
    private Movie movie;

    @ManyToMany(mappedBy = "ticketSet")
    private Set<Customer> customerSet;


}
