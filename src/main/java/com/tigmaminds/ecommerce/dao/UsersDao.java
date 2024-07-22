package com.tigmaminds.ecommerce.dao;

import com.tigmaminds.ecommerce.dto.UserDetailDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersDao {
    private final JdbcTemplate jdbcTemplate;

    public UsersDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    RowMapper<List<UserDetailDTO>> userDetailsMapper(){
        return new RowMapper<List<UserDetailDTO>>() {
            @Override
            public List<UserDetailDTO> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<UserDetailDTO> userDetailDTOList = new ArrayList<>();
                do{
                    userDetailDTOList.add(new UserDetailDTO(rs.getString("username")
                            ,rs.getString("password")));
                }while (rs.next());
                return userDetailDTOList;
            }
        };
    }

    public UserDetailDTO getUserByUsernameAndPassword(String username, String password){
        UserDetailDTO userDetailDTO = jdbcTemplate.queryForObject("select * from users where username = ? and password = ?",
                new Object[]{username, password}, userDetailsMapper()).get(0);
        return userDetailDTO;
    }

    public int doesUserWithPassExists(String username, String password){
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? and password = ?";
        Integer count = jdbcTemplate.queryForObject(sql,new Object[]{username,password},Integer.class);
        return count;
    }

    public int setUserByUsernamAndPassword(String username, String password){
        return jdbcTemplate.update("insert into users ( username, password ) values ( ? , ? )",username,password);
    }

    public int doesUserExist(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count;
    }


}
