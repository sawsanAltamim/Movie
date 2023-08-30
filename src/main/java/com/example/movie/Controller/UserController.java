package com.example.movie.Controller;

import com.example.movie.Api.ApiResponse;
import com.example.movie.Service.UserService;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        userService.register(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("user registered"));
    }

    @PostMapping("/admin")
    public ResponseEntity registerAdmin(@RequestBody User user){
        userService.registerAdmin(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("admin registered"));
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(HttpStatus.OK).body("logout");
    }

}
