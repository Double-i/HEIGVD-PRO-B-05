package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.exceptions.authentication.AccessDeniedException;
import ch.heigvd.easytoolz.models.AuthenticationRequest;
import ch.heigvd.easytoolz.models.dto.AuthentificationResponse;
import ch.heigvd.easytoolz.models.json.SuccessResponse;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.util.JwtUtil;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
class AuthenticationController {
    @Value("${ch.heigvd.easytools.jwtToken.accessToken}")
    private String accessTokenName;

    @Value("${ch.heigvd.easytools.jwtToken.duration}")
    private String duration;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    /**
     * authenticates the used asked in the request
     * @param authenticationRequest the request of the user
     * @return the responseEntity which should contain the cookie
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){

        if(!authenticationService.authenticateUser(authenticationRequest.getUserName(), authenticationRequest.getPassword()))
            throw new BadCredentialsException("Incorrect username or password");

        return generateResponseEntity(authenticationRequest.getUserName());
    }

    /**
     * refresh the authToken
     * @return the request HTTP with a new cookie
     */
    @GetMapping("/authrefresh")
    public ResponseEntity<?> refreshToken(){
        User userDetails = authenticationService.getTheDetailsOfCurrentUser();
        if(userDetails != null){
            return generateResponseEntity(userDetails.getUserName());
        }else{
            throw new AccessDeniedException();
        }
    }

    /**
     * signs up the user passed in parameter
     * @param user
     * @return a HTTP OK status with a success message
     */
    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<JSONObject> signUp(@RequestBody User user){
        user.setAdmin(false);
        userService.storeUser(user);
        return ResponseEntity.ok().body(new SuccessResponse("The user has been stored"));
    }

    /**
     * logs out the current connected user
     * @return a HTTP OK status with a success message
     */
    @GetMapping(value = "/logout")
    public  ResponseEntity<?> logOut(){

        final String jwt = ""; // create a empty token

        // store jwt into a http cookie to avoid cookie theft by XSS attack
        HttpCookie cookie = ResponseCookie.from(accessTokenName, jwt)
                .httpOnly(true)
                .path("/")
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().headers(responseHeaders).body(new SuccessResponse("The user has been logged out"));

    }

    /**
     * generates a response entity with the username passed in parameter
     * @param username the username of the user which will be connected
     * @return the response entity generated
     */
    private ResponseEntity<?> generateResponseEntity(String username){
        User userDetails = authenticationService.loadByUsername(username);

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // store jwt into a http cookie to avoid cookie theft by XSS attack
        HttpCookie cookie = ResponseCookie.from(accessTokenName, jwt)
                .maxAge(Integer.parseInt(duration))
                .httpOnly(true)
                .path("/")
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + Integer.parseInt(duration) * 1000 );

        AuthentificationResponse response = new AuthentificationResponse(userDetails, expirationDate);

        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }
}
