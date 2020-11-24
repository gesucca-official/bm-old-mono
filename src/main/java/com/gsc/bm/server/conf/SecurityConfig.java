package com.gsc.bm.server.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(environment.getProperty("auth.username"))
                .password("{noop}" + environment.getProperty("auth.password"))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                .authorizeRequests()
                .antMatchers("/control-panel/**").authenticated()
                .anyRequest().permitAll();
    }

    @RestController
    public static class UnauthorizedAccessController {
        @GetMapping("/unauthorized")
        public void bounceUnauthenticatedGuys() {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}

