package com.example.movie.Repostory;

import com.example.movie.Table.Customer;
import com.example.movie.Table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepostory extends JpaRepository<Customer, Integer> {
    Customer findCustomerById(Integer customer_id);

    List<Customer> findAllByUser(User user);
}
