package com.tigmaminds.ecommerce.service;

import com.tigmaminds.ecommerce.dto.UserDetailDTO;

public interface BaseService {

    public int signInUser(UserDetailDTO userDetailDTO);

    public boolean signUpUser(UserDetailDTO userDetailDTO);

}
