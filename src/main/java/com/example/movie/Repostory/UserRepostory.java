package com.example.movie.Repostory;

import com.example.movie.Table.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostory extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);

    User findUserById(Integer id);
}
