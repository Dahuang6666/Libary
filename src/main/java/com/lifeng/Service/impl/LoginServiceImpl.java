package com.lifeng.Service.impl;

import com.lifeng.Mapper.LoginMapper;
import com.lifeng.Pojo.Login;
import com.lifeng.Pojo.LoginDTO;
import com.lifeng.Service.LoginService;
import com.lifeng.Utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private MD5 md5;

    @Override
    public boolean login(Login login) {
        if(login.getPermissions()!=1)//管理员登录
        {
            return loginMapper.login(login) != null;
        }
        else{
            //md5加密后匹配
            login.setCreateTime(LocalDateTime.now());
            login.setPassword(md5.encrypt(login.getPassword()));
            return loginMapper.login(login) != null;
        }
    }

    @Override
    public String register(Login login) {
        try {
            if(login.getPermissions()!=1)
            {
                loginMapper.register(login);
                return "True";
            }
            else{
                login.setCreateTime(LocalDateTime.now());
                login.setPassword(md5.encrypt(login.getPassword()));
                loginMapper.register(login);
                return "True";
            }
        } catch (DuplicateKeyException e) {
            return "False";
        }

    }

    @Override
    public List<LoginDTO> getLogin() {
        return loginMapper.getLogin();
    }

    @Override
    public void update(Login login) {
        if(login.getPermissions()==6)
        {
            loginMapper.update(login);
        }
        else{
            login.setPassword(md5.encrypt(login.getPassword()));
            loginMapper.update(login);
        }

    }

    @Override
    public Integer selectIdByUsername(String username) {
     return   loginMapper.selectIdByUsername(username);
    }
}
