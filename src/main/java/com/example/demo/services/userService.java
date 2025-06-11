package com.example.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repository.userRepository;

@Component
@Slf4j
public class userService {

    @Autowired
    private userRepository userrepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//    private static final Logger log = LoggerFactory.getLogger(userService.class);
//    we don't need this if we use @Slf4j

    // service method to add a person on mongoDB
    public void addUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            user.setRole(Arrays.asList("user"));
            userrepo.save(user);
        }
        catch(Exception e){
            log.info("this is info logger");
            log.warn("this is waring logger");
            log.error("Error occured for {} : ", user.getUsername(), e);
            log.info("this is info logger");
            log.debug("this is debug logger");
//            throw new RuntimeErrorException("error in saving user");
        }
    }

    // service method to retrieve all persons in person collections
    public List<User> getAll() {
        return userrepo.findAll(); // pre-defined method in MongoRepository 
    }

    // service method to find a person by id
    public Optional<User> getuserById(String id) {
        return userrepo.findById(id);

    }

    // service method to find a person by id
    public User findUserByUsername(String username) {
        return userrepo.findByUsername(username);

    }

    // service method to delete a person by id
    public int deleteById(String id) {
        if (userrepo.existsById(id)) {
            userrepo.deleteById(id);
            return 1;
        } else
            return -1;
    }

    // service method to update a person by id
    public Optional<User> updateById(String id) {
        return userrepo.findById(id);

    }


}
