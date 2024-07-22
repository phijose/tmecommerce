package com.tigmaminds.ecommerce.controller;

import com.tigmaminds.ecommerce.dto.ResponseDTO;
import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import com.tigmaminds.ecommerce.service.BaseService;
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

    private final BaseService baseService;
    private final UserDetailsValidator userDetailsValidator;

    public AuthController(BaseService baseService, UserDetailsValidator userDetailsValidator){
        this.baseService = baseService;
        this.userDetailsValidator = userDetailsValidator;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDetailDTO userDetailDTO,
                                              HttpServletRequest request,BindingResult bindingResult){
        Map<String, String> validationResult = userDetailsValidator.validate(userDetailDTO);
//        if (!validationResult.isEmpty()) {
//            return new ResponseEntity<>(new ResponseDTO<Map<String, String>>(validationResult
//                    ,request.getRequestURI()), HttpStatus.BAD_REQUEST);
//        }
        if(baseService.signUpUser(userDetailDTO))
            return new ResponseEntity<>(new ResponseDTO<String>("User Inserted 1 row created"
                    ,request.getRequestURI()), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ResponseDTO<String>("Same Username already exists"
                    ,request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserDetailDTO userDetailDTO,
                                    HttpServletRequest request,BindingResult bindingResult) {
        Map<String, String> validationResult = userDetailsValidator.validate(userDetailDTO);
//        if (!validationResult.isEmpty()) {
//            return new ResponseEntity<>(new ResponseDTO<Map<String, String>>(validationResult
//                    ,request.getRequestURI()), HttpStatus.BAD_REQUEST);
//        }
        try {
            int result = baseService.signInUser(userDetailDTO);
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
