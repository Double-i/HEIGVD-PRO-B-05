package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.controllers.exceptions.user.UserAlreadyPresent;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserFailedDeleteException;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserFailedStoreException;
import ch.heigvd.easytoolz.controllers.exceptions.user.UserNotFoundException;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.models.User;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static ch.heigvd.easytoolz.utils.Utils.transformLike;

// TODO : One day remove this comment
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
// https://spring.io/guides/tutorials/rest/
// !! Pour la recherche
// https://blog.tratif.com/2017/11/23/effective-restful-search-api-in-spring/

@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<User> index(
            @RequestParam(value="firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "email", required = false) String email
    ){
        firstName = transformLike(firstName);
        lastName = transformLike(lastName);
        userName = transformLike(userName);
        email = transformLike(email);

        if(firstName != null){
            if(lastName != null){
                return userRepository.findByFirstNameLikeAndLastNameLike(firstName, lastName);
            }else{
                return userRepository.findByFirstNameLike(firstName);
            }
        }else{
            if(lastName != null){
                return userRepository.findByLastNameLike(lastName);
            }else if(userName != null){
                return userRepository.findByUserNameLike(userName);
            }else if(email != null){
                return userRepository.findByEmailLike(email);
            }
            else{
                return userRepository.findAll();
            }
        }
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
                    if(newUser.getEmail() != null) oldUser.setEmail(newUser.getEmail());
                    return userRepository.save(oldUser);
                })
                .orElseThrow(
                    () -> new UserNotFoundException(username)
                );
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> delete(@PathVariable String username){
        Optional<User> user = userRepository.findById(username);
        if(user.isPresent()){
            userRepository.delete(user.get());
            if (userRepository.findById(username).isPresent()) {
                throw new UserFailedDeleteException(username);
            } else {
                return new ResponseEntity<>("The user has been deleted", HttpStatus.OK);
            }
        }else{
            throw new UserNotFoundException(username);
        }
    }

    @PostMapping
    public ResponseEntity<String> store(@RequestBody User user){
        if(userRepository.findById(user.getUserName()).isPresent()){
            throw new UserAlreadyPresent(user.getUserName());
        }else{
            if(userRepository.save(user).equals(user)){
                return new ResponseEntity<>("The user has been stored", HttpStatus.OK);
            }else{
                throw new UserFailedStoreException(user.getUserName());
            }
        }
    }
}
