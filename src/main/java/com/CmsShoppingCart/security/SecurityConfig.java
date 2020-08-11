package com.CmsShoppingCart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CmdUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                 .authorizeRequests()
                    .antMatchers("/category/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/").permitAll()
                        .and()
                            .formLogin()
                                .loginPage("/login")
                                .usernameParameter("email")
                        .and()
                            .logout()
                                .logoutSuccessUrl("/")
                        .and()
                            .exceptionHandling()
                                .accessDeniedPage("/404");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(AuthenticationProvider());

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());

    }

//    @Bean
//    public AuthenticationProvider AuthenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
//
//        return authenticationProvider;
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
