package com.lifeng.Mapper;


import com.lifeng.Pojo.Login;
import com.lifeng.Pojo.LoginDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LoginMapper {
    @Select("select * from library.login where username=#{username} and password=#{password} and permissions=#{permissions}")
    Login login(Login login);

    @Insert("insert into library.login(username, password, createTime, permissions,email)" +
            "values (#{username},#{password},#{createTime},#{permissions},#{email})")
    void register(Login login);

    @Select("select id, username, permissions, email from library.login")
    List<LoginDTO> getLogin();

    @Select("select email from library.login where username =#{username}")
    String getEmail(String username);

    void update(Login login);

    @Select("select id from library.login where username=#{username}")
    Integer selectIdByUsername(String username);
}
