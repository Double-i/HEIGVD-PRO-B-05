package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.AuthenticationRequest;
import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.services.interfaces.UserService;
import ch.heigvd.easytoolz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@RestController
class AuthenticationController {
    @Value("${ch.heigvd.easytools.jwtToken.accessToken}")
    private String accesTokenName;

    @Value("${ch.heigvd.easytools.jwtToken.duration}")
    private String duration;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        if(!authenticationService.authenticateUser(authenticationRequest.getUserName(), authenticationRequest.getPassword()))
            throw new BadCredentialsException("Incorrect username or password");

        final User userDetails = authenticationService.loadByUsername(authenticationRequest.getUserName());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // store jwt into a http cookie to avoid cookie theft by XSS attack
        HttpCookie cookie = ResponseCookie.from(accesTokenName, jwt)
                .maxAge(Integer.valueOf(duration))
                .httpOnly(true)
                .path("/")
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().headers(responseHeaders).body(userDetails);

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user){
        userService.storeUser(user);
        return ResponseEntity.ok().body("The user has been stored");
    }

}
