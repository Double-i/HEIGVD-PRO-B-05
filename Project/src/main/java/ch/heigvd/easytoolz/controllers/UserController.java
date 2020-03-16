package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference

@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> index(){
        return userRepository.findAll();
    }

    @RequestMapping("/{username}")
    public Optional<User> show(@PathVariable String username){
        return userRepository.findById(username);
    }

    @RequestMapping("/search")
    public List<User> search(@RequestBody Map<String, String> body){
        String firstName = body.get("firstName");
        String lastName = body.get("lastname");

        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
