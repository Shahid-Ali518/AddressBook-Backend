package com.example.demo.controller;

import com.example.demo.cache.AppCache;
import com.example.demo.entity.User;
import com.example.demo.services.userService;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private userService userservice;

    @Autowired
    private AppCache appCache;

    @Autowired
    private AuthenticationManager authenticationManager; // internally use userdetailserviceimpl to fetch user detail

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private JwtUtils jwtUtils;

    // method to add person using json format using postman software
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            userservice.addUser(user);
            String message = "User Registered Successfully";
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);

        }
    }

    // method to add person using json format using postman software
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword()
            ));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            String token = jwtUtils.generateToken(userDetails.getUsername());

            return new ResponseEntity<>(token, HttpStatus.CREATED);
        }
        catch (Exception e) {

            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);

        }
    }

    // method to clear app cache so we don't need to restart the whole app
    @GetMapping("clear-app-cache")
    public ResponseEntity<String> ClearCache(){
        appCache.init();

        return new ResponseEntity<>("App Cleared", HttpStatus.OK);
    }

}
