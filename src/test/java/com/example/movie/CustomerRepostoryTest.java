package com.example.movie;


import com.example.movie.Repostory.CustomerRepostory;
import com.example.movie.Repostory.MovieRepostory;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.Customer;
import com.example.movie.Table.Movie;
import com.example.movie.Table.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepostoryTest {

    @Autowired
    UserRepostory userRepostory;

    @Autowired
    CustomerRepostory customerRepostory;

    User user;
    Customer customer1;
    Customer customer2;

    List<Customer> customers;

    @BeforeEach
    void setUp() {
        user = new User(null,"sawsan", "s123", "CUSTOMER", null);
        customer1 = new Customer(null,"Sawsan", "sawsan@gmail.com", "1234567890", true, 100.0, user, new HashSet<>());
        user.setCustomer(customer1);
        customerRepostory.save(customer1);
    }

    @Test
    public void findCustomerByIdTest() {
        Customer customer = customerRepostory.findCustomerById(customer1.getId());
        Assertions.assertThat(customer).isEqualTo(customer1);
    }

    @Test
    public void findAllByUserTest() {
        customers = customerRepostory.findAllByUser(user);
        Assertions.assertThat(customers).containsExactlyInAnyOrder(customer1);
    }
}