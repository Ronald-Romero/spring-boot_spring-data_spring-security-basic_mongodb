package com.springdata.demo.services;

import com.springdata.demo.entity.LoginUser;
import com.springdata.demo.payload.request.UserRequest;
import com.springdata.demo.repository.UserRepository;
import com.springdata.demo.utilities.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginUser userRegistration(LoginUser user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException(Messages.getMessage(Messages.MessageKey.USER_EXISTS, user.getUsername()));
        }
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        return userRepository.save(user);
    }

    public LoginUser userLogin(UserRequest userRequest) {
        // Search for the user by their username
        LoginUser user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new RuntimeException(Messages.getMessage(Messages.MessageKey.USER_NO_FOUND,  userRequest.getUsername())));
        // Validate the password using PasswordEncoder
        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPwd())) {
            throw new RuntimeException(Messages.getMessage(Messages.MessageKey.INCORRECT_PASSWORD));
        }
        // If the password is valid, return the user
        return user;
    }
}
