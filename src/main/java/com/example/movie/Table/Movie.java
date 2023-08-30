package com.example.movie.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(30) not null")
    private String movieName;

    @NotEmpty
    @Column(columnDefinition = "varchar(200) not null")
    private String movieDetails;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String director;

    @NotEmpty
    private String movieHours;

    @NotEmpty
    @Column(columnDefinition = "varchar(10) not null")
    private String movieType;

    @NotNull
    @PositiveOrZero
    @Max(value = 10)
    private Double movieEvaluation;

    @OneToMany(mappedBy = "movie")
    private List<Ticket> tickets;

}
