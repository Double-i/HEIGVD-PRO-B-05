package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.controllers.exceptions.UserNotFoundException;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
// https://spring.io/guides/tutorials/rest/

@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> index(){
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public User show(@PathVariable String username){
        return userRepository
                .findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @PutMapping("/{username}")
    public User update(@RequestBody User newUser, @PathVariable String username){
        return userRepository.findById(username)
                .map(oldUser -> {
                    if(newUser.getFirstName() != null) oldUser.setFirstName(newUser.getFirstName());
                    if(newUser.getLastName() != null) oldUser.setLastName(newUser.getLastName());
                    if(oldUser.isAdmin() != newUser.isAdmin()) oldUser.setAdmin(newUser.isAdmin());
                    return userRepository.save(oldUser);
                })
                .orElseThrow(
                    () -> new UserNotFoundException(username)
                );
    }

    @GetMapping("/search")
    public List<User> search(@RequestBody Map<String, String> body){
        String firstName = body.get("firstName");
        String lastName = body.get("lastname");

        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @PostMapping
    public User store(@RequestBody User user){
        return userRepository.save(user);
    }
}
