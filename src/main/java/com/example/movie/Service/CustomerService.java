package com.example.movie.Service;


import com.example.movie.Api.ApiException;
import com.example.movie.DTO.UserDTO;
import com.example.movie.Repostory.CustomerRepostory;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.Customer;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final UserRepostory userRepostory;
    private final CustomerRepostory customerRepostory;


    public List<Customer> getAllCustomers() {
        return customerRepostory.findAll();
    }

    public List<Customer> getCustomer(Integer user_id){
        User user = userRepostory.findUserById(user_id);
        return customerRepostory.findAllByUser(user);
    }

    public void addCustomer(Integer user_id, UserDTO userDTO) {
        User user = userRepostory.findUserById(user_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        Customer customer = new Customer();
        customer.setName(userDTO.getName());
        customer.setEmail(userDTO.getEmail());
        customer.setNumber(userDTO.getNumber());
        customer.setDiscountCoupon(userDTO.getDiscountCoupon());
        customer.setBalance(userDTO.getBalance());
        customer.setUser(user);

        customerRepostory.save(customer);
    }

    public void updateCustomer(Integer user_id ,Integer customer_id, UserDTO userDTO){
        Customer customer = customerRepostory.findCustomerById(customer_id);
        if(customer == null){
            throw new ApiException("Customer not found");
        }
        User user = userRepostory.findUserById(user_id);
        if(!customer.getUser().equals(user)){
            throw new ApiException("not found");
        }
        customer.setName(userDTO.getName());
        customer.setEmail(userDTO.getEmail());
        customer.setNumber(userDTO.getNumber());
        customer.setDiscountCoupon(userDTO.getDiscountCoupon());
        customer.setBalance(userDTO.getBalance());

        customerRepostory.save(customer);
    }

    public void deleteCustomer(Integer user_id ,Integer customer_id) {
        Customer customer = customerRepostory.findCustomerById(customer_id);
        if(customer == null){
            throw new ApiException("Customer not found");
        }
        User user = userRepostory.findUserById(user_id);
        if(!customer.getUser().equals(user)){
            throw new ApiException("not found");
        }
        customerRepostory.delete(customer);
    }
}
