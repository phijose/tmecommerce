package com.tigmaminds.ecommerce.service.impl;

import com.tigmaminds.ecommerce.dao.UsersDao;
import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import com.tigmaminds.ecommerce.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    UsersDao usersDao;

    public AuthServiceImpl(UsersDao usersDao){
        this.usersDao = usersDao;
    }

    @Override
    public int signInUser(UserDetailDTO userDetailDTO) {
        int count = usersDao.doesUserExist(userDetailDTO.getUsername());
        if(count>0){
            count = usersDao.doesUserWithPassExists(userDetailDTO.getUsername(),userDetailDTO.getPassword());
            if(count>0){
                return 200;
            }else {
                return 401;
            }
        }else{
            return 404;
        }
    }

    @Override
    public boolean signUpUser(UserDetailDTO userDetailDTO) {
        int count = usersDao.doesUserExist(userDetailDTO.getUsername());
        if(count>0){
            return false;
        }else{
            int rowsAffected = usersDao.setUserByUsernamAndPassword(userDetailDTO.getUsername(),userDetailDTO.getPassword());
            return rowsAffected>0;
        }
    }

    @Override
    public Boolean isAuthorized(String username) {
        return usersDao.isAdmin(username);
    }
}
