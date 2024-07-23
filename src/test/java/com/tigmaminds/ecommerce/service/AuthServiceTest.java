package com.tigmaminds.ecommerce.service;

import com.tigmaminds.ecommerce.dao.UsersDao;
import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import com.tigmaminds.ecommerce.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthServiceTest {

    @InjectMocks
    AuthServiceImpl baseService;

    @Mock
    UsersDao usersDao;

    UserDetailDTO userDetailDTO = new UserDetailDTO("phijose@gmail.com","1234qwer","user");

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void singInTest(){
        int actaul;
//        correct case
        when(usersDao.doesUserExist(userDetailDTO.getUsername()))
                .thenReturn(1);
        when(usersDao.doesUserWithPassExists(userDetailDTO.getUsername(),userDetailDTO.getPassword()))
                .thenReturn(1);
        actaul=baseService.signInUser(userDetailDTO);
        assertThat(actaul).isEqualTo(200);
//        bad password case
        when(usersDao.doesUserExist(userDetailDTO.getUsername()))
                .thenReturn(1);
        when(usersDao.doesUserWithPassExists(userDetailDTO.getUsername(),userDetailDTO.getPassword()))
                .thenReturn(0);
        actaul=baseService.signInUser(userDetailDTO);
        assertThat(actaul).isEqualTo(401);
//         bad username case
        when(usersDao.doesUserExist(userDetailDTO.getUsername()))
                .thenReturn(0);
        when(usersDao.doesUserWithPassExists(userDetailDTO.getUsername(),userDetailDTO.getPassword()))
                .thenReturn(0);
        actaul=baseService.signInUser(userDetailDTO);
        assertThat(actaul).isEqualTo(404);
    }
    @Test
    public void singUpTest(){
//        add user with new data
        when(usersDao.setUserByUsernamAndPassword(userDetailDTO.getUsername(),userDetailDTO.getPassword()))
                .thenReturn(1);
        assertThat(baseService.signUpUser(userDetailDTO)).isEqualTo(true);
//        add user with replicating data
        when(usersDao.setUserByUsernamAndPassword(userDetailDTO.getUsername(),userDetailDTO.getPassword()))
                .thenReturn(0);
        assertThat(baseService.signUpUser(userDetailDTO)).isEqualTo(false);
    }

}
