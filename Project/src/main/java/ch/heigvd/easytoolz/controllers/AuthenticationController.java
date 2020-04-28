package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.AuthenticationRequest;
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
import org.springframework.web.client.HttpClientErrorException;


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

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){

        if(!authenticationService.authenticateUser(authenticationRequest.getUserName(), authenticationRequest.getPassword()))
            throw new BadCredentialsException("Incorrect username or password");

        final User loggedUser = authenticationService.loadByUsername(authenticationRequest.getUserName());

        final String jwt = jwtTokenUtil.generateToken(loggedUser);

        // store jwt into a http cookie to avoid cookie theft by XSS attack
        HttpCookie cookie = ResponseCookie.from(accessTokenName, jwt)
                .maxAge(Integer.parseInt(duration))
                .httpOnly(true)
                .path("/")
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().headers(responseHeaders).body(loggedUser);

    }

    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<JSONObject> signUp(@RequestBody User user){
        user.setAdmin(false);
        userService.storeUser(user);
        return ResponseEntity.ok().body(new SuccessResponse("The user has been stored"));
    }

}
