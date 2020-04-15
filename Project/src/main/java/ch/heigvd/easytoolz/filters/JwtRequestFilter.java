package ch.heigvd.easytoolz.filters;


import ch.heigvd.easytoolz.models.User;
import ch.heigvd.easytoolz.services.interfaces.AuthenticationService;
import ch.heigvd.easytoolz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${ch.heigvd.easytools.jwtToken.accessToken}")
    private String cookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {



        // old way
        //final String authorizationHeader = request.getHeader("Authorization");
        // todo : delete me ! mtn le token est récupérer via le cookie fourni automatiquement par le navigateur web.
        Cookie accessCookie = getCookieByName(request.getCookies(), "accessToken");

        String username = null;
        String jwt = null;

        // todo : devrait -on vérifier que le cookie est httpOnly
        // todo : si oui comment faire car actuellement uniquement le
        if (accessCookie != null && !accessCookie.getValue().isEmpty()) {
            jwt = accessCookie.getValue();
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User userDetails = this.authenticationService.loadByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, null);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                
            }
        }
        chain.doFilter(request, response);
    }
    private Cookie getCookieByName(Cookie[] cookies, String name ){
        Cookie requestedCookie = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    requestedCookie =  cookie;
                    break;
                }
            }
        }
        return requestedCookie;
    }
}
