package com.example.demowithtests.util.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    // TODO: 18-Oct-22 Create 2 users for demo
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("select username,authority from secauthorities where username = ?")
                .usersByUsernameQuery("select username,password,enabled from secusers where username = ?")
                .passwordEncoder(new BCryptPasswordEncoder());

        /*
        auth.inMemoryAuthentication()

                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
         */

    }

    // TODO: 18-Oct-22 Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/api/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/passports/**").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/api/passports/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/passports/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/passports/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/cabinets/**").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/api/cabinets/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/cabinets/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/cabinets/**").hasAuthority("ADMIN")

                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
