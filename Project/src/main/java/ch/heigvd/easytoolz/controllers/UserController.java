package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.controllers.exceptions.UserNotFoundException;
import ch.heigvd.easytoolz.repositories.UserRepository;
import ch.heigvd.easytoolz.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * transform any string in LIKE string for the query
     * for example :
     * s => 'henri'
     * return => '%henri%'
     * @param s a string
     * @return the string updated or null if s == null
     */
    private String transformLike(String s){
        if(s == null)
            return null;
        return "%" + s + "%";
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
    public void delete(@PathVariable String username){
        userRepository.findById(username).ifPresentOrElse(user -> {
            userRepository.delete(user);
        }, () -> {
            throw new UserNotFoundException(username);
        });
    }

    @PostMapping
    public User store(@RequestBody User user){
        if(userRepository.findById(user.getUserName()).isPresent()){
            return null;
        }else{
            return userRepository.save(user);
        }
    }
}
