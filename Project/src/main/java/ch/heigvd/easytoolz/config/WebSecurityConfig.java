package ch.heigvd.easytoolz.config;

import ch.heigvd.easytoolz.components.EasyAuthenticationProvider;
import ch.heigvd.easytoolz.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("ch.heigvd.easytoolz")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public EasyAuthenticationProvider easyAuthenticationProvider;
    @Autowired
    public JwtRequestFilter jwtRequestFilter;

    /**
     * updates the authentification with a new authentication provider
     * @param auth the manager which must be updated
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(easyAuthenticationProvider);
    }

    /**
     * @return the bean for the password encoder (BCryptPasswordEncoder())
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * configures the URL allowed by the application without authorization
     * @param httpSecurity the security HTTP
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/api/authenticate","/api/signup").permitAll() // accept login endpoint
                .antMatchers("/api/**").authenticated() // block every other backend endpoint for now
                .antMatchers("/**").permitAll().and()   // allow every other URI since all will be redirect to index.html
                .exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


}