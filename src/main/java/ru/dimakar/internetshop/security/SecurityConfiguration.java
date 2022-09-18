package ru.dimakar.internetshop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dimakar.internetshop.dto.UserRoles;
import ru.dimakar.internetshop.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Configuration
@EnableWebSecurity() 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // TODO: 18.09.2022 remove deprecated WebSecurityConfigurerAdapter 

    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/product").permitAll()
                .mvcMatchers("/api/admin/**").hasAnyAuthority(UserRoles.ROLE_ADMINISTRATOR.toString())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    Map<String, Object> body = Map.of(
                            "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                            "status", HttpStatus.FORBIDDEN.value(),
                            "error", "Forbidden",
                            "message", "Access Denied!",
                            "path", request.getRequestURI()
                    );
                    response.getOutputStream()
                            .println(new ObjectMapper().writeValueAsString(body));
                });
    }

}
