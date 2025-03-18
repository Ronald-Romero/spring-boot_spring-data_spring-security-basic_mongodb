package com.springdata.demo.controllers;

import com.springdata.demo.entity.LoginUser;
import com.springdata.demo.payload.request.UserRequest;
import com.springdata.demo.payload.response.UserLoginResponse;
import com.springdata.demo.services.AuthService;
import com.springdata.demo.utilities.Messages;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * Endpoint for user registration.
     *
     * @param user Object of type LoginUser received in the request body (RequestBody).
     *        This object contains the information needed for user registration and is
     *        automatically validated using the @Valid annotation.
     * @return Returns a ResponseEntity with the result of the operation:
     *         - If the registration is successful, the registered user is returned with an HTTP code 200 (OK).
     *         - If an error occurs (for example, an invalid argument), it returns an error
     *           message with an HTTP code of 400 (Bad Request).
     *
     * Example of request:
     * POST /api/auth/registration
     */
    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody LoginUser user){
        try {
            LoginUser newUser = authService.userRegistration(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException ex) {
            logger.info("AuthController.userRegistration: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Messages.getMessage(Messages.MessageKey.ERROR_CREATING_USER));
        }
    }

    /**
     * Endpoint to perform a user login.
     *
     * @param userRequest Object containing the user credentials.
     * @return ResponseEntity with the UserLoginResponse object if the
     *         credentials are correct,or an HTTP 401 status if an error occurs.
     *
     * Example of request:
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody UserRequest userRequest) {
        try {
            LoginUser loginUser = authService.userLogin(userRequest);
            return ResponseEntity.ok(new UserLoginResponse(loginUser));
        } catch (AuthenticationException ex) {
            logger.info("AuthController.userLogin: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Messages.getMessage(Messages.MessageKey.INCORRECT_PASSWORD));
        }
    }
}
