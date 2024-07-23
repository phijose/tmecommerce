package com.tigmaminds.ecommerce.service;

import com.tigmaminds.ecommerce.dto.UserDetailDTO;

public interface AuthService {

    public int signInUser(UserDetailDTO userDetailDTO);

    public boolean signUpUser(UserDetailDTO userDetailDTO);

    public Boolean isAuthorized(String username);

}
