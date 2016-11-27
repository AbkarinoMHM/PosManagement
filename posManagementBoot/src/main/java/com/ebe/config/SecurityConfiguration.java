package com.ebe.config;

/**
 * Created by saado on 11/10/2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableSpringDataWebSupport
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * This section defines the user accounts which can be used for
     * authentication as well as the roles each user has.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user@ebe.com").password("user").roles("USER").and()
                .withUser("admin@ebe.com").password("admin").roles("USER", "ADMIN");
    }

    /**
     * This section defines the security policy for the app.
     * - BASIC authentication is supported (enough for this REST-based demo)
     * - /employees is secured using URL security shown below
     * - CSRF headers are disabled since we are only testing the REST interface,
     * not a web one.
     * <p>
     * NOTE: GET is not shown which defaults to permitted.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .httpBasic().and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/services/setting").permitAll()
//               //.antMatchers(HttpMethod.GET, "/**/**").hasRole("USER")
//                .antMatchers(HttpMethod.POST, "/services/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/services/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/services/**").hasRole("ADMIN")
//                .and()
//                .formLogin().loginPage("/").permitAll()
//                .and()
//                .csrf().disable();

        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/services/setting").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.POST, "/services/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/services/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/services/**").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();

    }
}
