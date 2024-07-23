package com.tigmaminds.ecommerce.controller;

import com.tigmaminds.ecommerce.dto.ResponseDTO;
import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import com.tigmaminds.ecommerce.service.AuthService;
import com.tigmaminds.ecommerce.validator.UserDetailsValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserDetailsValidator userDetailsValidator;

    public AuthController(AuthService authService, UserDetailsValidator userDetailsValidator){
        this.authService = authService;
        this.userDetailsValidator = userDetailsValidator;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDetailDTO userDetailDTO,
                                              HttpServletRequest request,BindingResult bindingResult){
        if(authService.signUpUser(userDetailDTO))
            return new ResponseEntity<>(new ResponseDTO<String>("User Inserted 1 row created"
                    ,request.getRequestURI()), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ResponseDTO<String>("Same Username already exists"
                    ,request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserDetailDTO userDetailDTO,
                                    HttpServletRequest request,BindingResult bindingResult) {
        try {
            int result = authService.signInUser(userDetailDTO);
            if (result == 200)
                return new ResponseEntity<>(new ResponseDTO<String>("Sign In Successfull"
                        , request.getRequestURI()), HttpStatus.OK);
            else if (result == 401)
                return new ResponseEntity<>(new ResponseDTO<String>("wrong password"
                        , request.getRequestURI()), HttpStatus.UNAUTHORIZED);
            else
                return new ResponseEntity<>(new ResponseDTO<String>("user does not exist, user signup to create a user"
                        , request.getRequestURI()), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
