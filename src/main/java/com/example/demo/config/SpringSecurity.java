package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurity  {


    @Autowired
    UserDetailsService userDetail;
    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain requestChain(HttpSecurity http) throws Exception{
         http.authorizeHttpRequests( request -> request
                .requestMatchers("/person/**").authenticated()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/admin/**").hasRole("admin")
                        .requestMatchers("/public/**").permitAll()
                .anyRequest().permitAll())
//                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)

                 // this filter the request on the base of jwt
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

         return http.build();


    }

    // method for authentication manager
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetail);
    }


    @Bean
    public PasswordEncoder password(){
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }



}
