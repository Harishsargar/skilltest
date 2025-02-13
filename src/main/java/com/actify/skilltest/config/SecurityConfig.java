package com.actify.skilltest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.actify.skilltest.Service.Impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService securityCustomUserDetailService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.println("verifying1.......");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        System.out.println("verifying.......");
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/api/public/**").permitAll()
            .requestMatchers("/api/manager/**").hasRole("MANAGER") // Manager APIs should only be accessible to users with the Manager role.
            .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Admin APIs should only be accessible to users with the Admin role
            .requestMatchers("/api/user/**").authenticated() //User APIs should be accessible to all authenticated users.
            // .requestMatchers("/api/manager/**").permitAll()

            .anyRequest().authenticated();
        })
        .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.realmName("SkilTest"));       
        return httpSecurity.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
