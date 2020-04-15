package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO : One day remove this comment
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
// https://spring.io/guides/tutorials/rest/
// !! Pour la recherche
// https://blog.tratif.com/2017/11/23/effective-restful-search-api-in-spring/

@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> index(
            @RequestParam(value="firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "email", required = false) String email
    ){
        return userService.filters(firstName, lastName, userName, email);
    }

    @GetMapping("/{username}")
    public User show(@PathVariable String username){
        return userService.getUser(username);
    }

    @PutMapping("/{username}")
    public User update(@RequestBody User newUser, @PathVariable String username){
        return userService.updateUser(newUser, username);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> delete(@PathVariable String username){
        userService.deleteUser(username);
        return new ResponseEntity<>("The user has been deleted", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> store(@RequestBody User user){
        userService.storeUser(user);
        return new ResponseEntity<>("The user has been stored", HttpStatus.OK);
    }
}
