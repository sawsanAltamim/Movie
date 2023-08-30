package com.example.movie.Controller;

import com.example.movie.Api.ApiResponse;
import com.example.movie.DTO.UserDTO;
import com.example.movie.Service.CustomerService;
import com.example.movie.Service.UserService;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    public final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    @GetMapping("/get-customer")
    public ResponseEntity getCustomer(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addCustomer(@AuthenticationPrincipal User user , @RequestBody UserDTO userDTO) {
        customerService.addCustomer(user.getId(), userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer added"));
    }

    @PutMapping("/update/{customer_id}")
    public ResponseEntity updateCustomer( @AuthenticationPrincipal User user ,@PathVariable Integer customer_id, @RequestBody UserDTO userDTO) {
        customerService.updateCustomer(user.getId(), customer_id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer updated"));
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user ,@PathVariable Integer customerId) {
        customerService.deleteCustomer(user.getId(),customerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Customer deleted"));
    }
}
