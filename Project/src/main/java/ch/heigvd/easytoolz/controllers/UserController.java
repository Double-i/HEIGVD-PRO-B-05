package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.json.SuccessResponse;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<JSONObject> delete(@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.ok(new SuccessResponse("The user has been deleted"));
    }

    @PostMapping
    public ResponseEntity<JSONObject> store(@RequestBody User user){
        userService.storeUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getUserName()).toUri();
        return ResponseEntity.created(uri).body(new SuccessResponse("The user has been stored"));
    }
}
