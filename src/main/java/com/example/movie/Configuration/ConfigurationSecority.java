package com.example.movie.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ConfigurationSecority {

    private final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()

                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // الجلسه تنشأ اذا كانت مطلوبه فقط
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll() //يسمح للجميع بالوصول إلى عنوان
                .requestMatchers("/api/v1/auth/admin").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout() // تسجيل الخروج
                .logoutUrl("/api/v1/auth/logout") // الباث حق تسجيل الخروج
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true) // إلغاء صلاحية الجلسة عند تسجيل الخروج
                .and()
                .httpBasic();
        return http.build();
    }
}
