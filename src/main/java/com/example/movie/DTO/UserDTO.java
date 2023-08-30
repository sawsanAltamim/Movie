package com.example.movie.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    //private Integer costumer_id;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty
    @Email
    //@Column(unique=true, columnDefinition = "varchar(20) not null")
    private String email;

    @NotEmpty
    //@Column(unique=true, columnDefinition = "varchar(10) not null")
    private String number;

    @NotNull
    private Boolean discountCoupon;

    @NotNull
    @PositiveOrZero
    private Double balance;
}
