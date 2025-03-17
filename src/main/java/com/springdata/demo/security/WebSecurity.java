package com.springdata.demo.security;

import com.springdata.demo.entity.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/public/**") // Ignore CSRF for specific public routes
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    /*
    ******* Local use **********
    * Manage users and their roles in a Spring Security application without
    *     relying on an external database.
    * Instead of loading users from a database, users and their credentials are manually
    *     configured in the server's memory.
    *
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("username")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build());

        manager.createUser(User.withUsername("Admin")
                .password(passwordEncoder.encode("adminpassword"))
                .roles("ADMIN")
                .build());
        return manager;
    }
    */

    @Bean
    public UserDetailsService userDetailsService(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
        return username -> {
            // Query the user from MongoDB
            Query query = new Query();
            query.addCriteria(Criteria.where("username").is(username));
            LoginUser userEntity = mongoTemplate.findOne(query, LoginUser.class);

            if (userEntity == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }

            return User.withUsername(userEntity.getUsername())
                    .password(userEntity.getPwd())
                    .roles(userEntity.getRoles().toArray(new String[0]))
                    .build();
        };
    }
     
}
