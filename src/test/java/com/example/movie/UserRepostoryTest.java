package com.example.movie;


import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepostoryTest {

    @Autowired
    UserRepostory userRepostory;

    User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "Majd", "1234", "CUSTOMER", null);
        userRepostory.save(user);
    }

    @Test
    public void findUserByUsernameTest() {
        User foundUser = userRepostory.findUserByUsername(user.getUsername());
        Assertions.assertThat(foundUser).isEqualTo(user);
    }

    @Test
    public void findUserByIdTest() {
        User foundUser = userRepostory.findUserById(user.getId());
        Assertions.assertThat(foundUser).isEqualTo(user);
    }
}

