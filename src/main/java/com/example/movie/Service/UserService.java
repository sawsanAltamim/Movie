package com.example.movie.Service;

import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepostory userRepostory;

    public void register (User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("CUSTOMER");

        userRepostory.save(user);
    }

    /*public void registerAdmin (User user){
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        user.setRole("ADMIN");

        userRepostory.save(user);
    }*/
}
