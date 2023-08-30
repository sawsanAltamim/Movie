package com.example.movie.Service;
import com.example.movie.Api.ApiException;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetilsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepostory userRepostory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepostory.findUserByUsername(username);
        if (user == null) {
            throw new ApiException("Wrong username or password");
        }
        return user;
    }
}
