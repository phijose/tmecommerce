package com.tigmaminds.ecommerce.validator;

import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserDetailsValidator{

    public boolean isValidEmail(String email) {
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String email) {
        String emailRegex = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Map<String, String> validate(UserDetailDTO userDetailDTO){
        Map<String, String> validationResult = new HashMap<>();
        if(!isValidEmail(userDetailDTO.getUsername()))
            validationResult.put("username","username should be an email address");
        if (!isValidPassword(userDetailDTO.getPassword()))
            validationResult.put("password","password should contain atleast 1 alphabet and number");
        if(!validationResult.isEmpty())
            validationResult.put("description","invalid username or password");
        return validationResult;
    }
}
