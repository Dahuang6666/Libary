package com.lifeng.Service;

import com.lifeng.Pojo.Login;
import com.lifeng.Pojo.LoginDTO;

import java.util.List;

public interface LoginService {
    boolean login(Login login);

    String register(Login login);

    List<LoginDTO> getLogin();

    void update(Login login);

    Integer  selectIdByUsername(String username);
}
