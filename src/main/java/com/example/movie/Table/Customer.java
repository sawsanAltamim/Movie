package com.example.movie.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty
    @Email
    @Column(unique=true, columnDefinition = "varchar(20) not null")
    private String email;

    @NotEmpty
    @Column(unique=true, columnDefinition = "varchar(10) not null")
    private String number;

    @NotNull
    private Boolean discountCoupon;

    @NotNull
    @PositiveOrZero
    private Double balance;

    @OneToOne
    @MapsId // بتاخذ الاي دي حق الكوستمر
    @JsonIgnore
    private User user;

    @ManyToMany
    @JsonIgnore
    private Set<Ticket> ticketSet;


}
