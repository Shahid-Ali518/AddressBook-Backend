package com.example.demo.controller;

import com.example.demo.api.response.CountryResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.userRepository;
import com.example.demo.services.CountryService;
import com.example.demo.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService userservice;


    @Autowired
    private userRepository userReop;


    @GetMapping("/home")
    public String getMethodName() {
        return "redirect:/home.html";
    }

    // method to get all persons
    @GetMapping
    public ResponseEntity<?> getallUsers() { // ? is wild card used for any return type
        List<User> all = userservice.getAll();
        if(all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>("No record found", HttpStatus.NOT_FOUND);

    }

    // method to get a person by id using url varibale
    @GetMapping("username/{myid}")
    public ResponseEntity<?> getById(@PathVariable String myid) {
        Optional<User> user = userservice.getuserById(myid); // optional if found return else return null
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK); // return entity with code
        return new ResponseEntity<>("No record Found", HttpStatus.NOT_FOUND); // return only httpstatus code
    }

    // done with spring security
    // method to delete user using username
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userReop.deleteUserByUsername(username);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
    }


    // method to delete a person by id using url varibale
//    @DeleteMapping("id/{myid}")
//    public ResponseEntity<?> deleteById(@PathVariable String myid) {
//        if (userservice.deleteById(myid) > 0)
//            return new ResponseEntity<>("Person by " + myid + " deleted successfully!", HttpStatus.OK);
//        else
//            return new ResponseEntity<>("Person does not exist in Database", HttpStatus.NOT_FOUND);
//    }


    // method to update person info
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User old = userservice.findUserByUsername(username);

        old.setUsername(user.getUsername());
        old.setPassword(user.getPassword());
        userservice.addUser(old);

        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);

    }
//

//    // method to update person info
//    @PutMapping
//    public ResponseEntity<?> updatePerson(@RequestBody User entity) {
//        User old = userservice.findUserByUsername(entity.getUsername());
//        if (old != null) {
//            old.setUsername(entity.getUsername());
//            old.setPassword(entity.getPassword());
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
//        return new ResponseEntity<>("No record Found or Invalid Input", HttpStatus.NOT_FOUND);
//    }


    // method to get info from external api like country
    @Autowired
    private CountryService  countryService;

    @Autowired
    private CountryResponse countryResponse;

    @GetMapping("/country")
    public ResponseEntity<?> getCountry(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<CountryResponse> countryResponse = countryService.getCountry("India");
        CountryResponse name = null ;
        String official = "";
        if(countryResponse != null){
             name = countryResponse.get(0);
//            official = countryResponse.getName().official;
            return new ResponseEntity<>(authentication.getName() + " " +  name.getName().toString() , HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
    }
}
