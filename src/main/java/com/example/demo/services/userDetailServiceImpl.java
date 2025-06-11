package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class userDetailServiceImpl implements UserDetailsService {

    @Autowired
    private userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userRepo.findByUsername(username);
        if(user != null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username " + username);
    }
}
