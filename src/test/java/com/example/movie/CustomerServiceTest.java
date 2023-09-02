package com.example.movie;

import com.example.movie.DTO.UserDTO;
import com.example.movie.Repostory.CustomerRepostory;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Service.CustomerService;
import com.example.movie.Table.Customer;
import com.example.movie.Table.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepostory customerRepostory;

    @Mock
    UserRepostory userRepostory;

    User user;
    Customer customer1;
    Customer customer2;
    List<Customer> customers;

    UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User(1, "Majd", "1234", "ADMIN", null);
        userRepostory.save(user);

        customer1 = new Customer(1, "Sawsan", "sawsan@gmail.com", "1234567890", true, 100.0, user, null);
        customer2 = new Customer(2, "Tamim", "tamim@gmail.com", "123455578", false, 200.0, user, null);
        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
    }

    @Test
    public void testGetCustomer() {
        when(userRepostory.findUserById(user.getId())).thenReturn(user);
        when(customerRepostory.findAllByUser(user)).thenReturn(customers);

        List<Customer> customerList = customerService.getCustomer(user.getId());

        Assertions.assertEquals(customerList, customers);

        verify(userRepostory,times(1)).findUserById(user.getId());
        verify(customerRepostory,times(1)).findAllByUser(user);
    }

    @Test
    public void updateCustomerTest() {
        userDTO = new UserDTO("Updated Name", "updatedemail@example.com", "9876543210", false, 50.0);
        when(customerRepostory.findCustomerById(customer1.getId())).thenReturn(customer1);
        when(userRepostory.findUserById(user.getId())).thenReturn(user);

        customerService.updateCustomer(user.getId(), customer1.getId(), userDTO);

        verify(customerRepostory, times(1)).findCustomerById(customer1.getId());
        verify(userRepostory, times(1)).findUserById(user.getId());
        verify(customerRepostory, times(1)).save(customer1);

        Assertions.assertEquals(customer1.getName(), userDTO.getName());
        Assertions.assertEquals(customer1.getEmail(), userDTO.getEmail());
        Assertions.assertEquals(customer1.getNumber(), userDTO.getNumber());
        Assertions.assertEquals(customer1.getDiscountCoupon(), userDTO.getDiscountCoupon());
        Assertions.assertEquals(customer1.getBalance(), userDTO.getBalance());
    }

    @Test
    public void deleteCustomer(){
        when(customerRepostory.findCustomerById(customer1.getId())).thenReturn(customer1);
        when(userRepostory.findUserById(user.getId())).thenReturn(user);


        customerService.deleteCustomer(user.getId(), customer1.getId());

        verify(customerRepostory, times(1)).findCustomerById(customer1.getId());
        verify(userRepostory, times(1)).findUserById(user.getId());
        verify(customerRepostory, times(1)).delete(customer1);
    }

}
