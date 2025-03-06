package com.lifeng.Service.impl;


import com.lifeng.Mapper.AdminMapper;
import com.lifeng.Pojo.*;
import com.lifeng.Service.AdminService;
import com.lifeng.Utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MD5 md5;
    @Override
    public void addBook(Books book) {
        adminMapper.addBook(book);
    }

    @Override
    public void deleteBook(Integer id) {
        adminMapper.deleteBook(id);
    }

    @Override
    public String updateBook(Books book) {
        int rowsUpdated = adminMapper.updateBook(book);
        if (rowsUpdated > 0) {
            return "True";
        } else {
            return "False";
        }
    }

    @Override
    public List<BorrowRequests> getBorrowDispose() {
        return  adminMapper.getBorrowDispose();
    }

    @Override
    public void UpdateBorrow(BorrowRequests borrowRequests) {
        adminMapper.UpdateBorrow(borrowRequests);
    }

    @Override
    public List<bookDTO> searchBooksByConditions(String name, String author, String categoryName) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("author", author);
        map.put("categoryName", categoryName);
        return adminMapper.searchBooksByConditions(map);
    }

    @Override
    public List<BookCategory> getAllCategories() {
        return  adminMapper.getAllCategories();
    }

    @Override
    public void addCategory(BookCategory category) {
        adminMapper.addCategory(category);
    }

    @Override
    public void deleteCategory(int id) {
        adminMapper.deleteCategory(id);
    }

    @Override
    public void resetPassword(Login login) {
        if(login.getPermissions()!=1){
            login.setPassword("123456");
        }
        else{
            login.setPassword(md5.encrypt("123456"));
        }
        adminMapper.resetPassword(login);
    }

    @Override
    public void updateLogin(Login login) {
        adminMapper.updateLogin(login);
    }

    @Override
    public void deleteLogin(int id) {
        adminMapper.deleteLogin(id);
    }

    @Override
    public List<BorrowRequests> getBorrowUndispose() {
       return  adminMapper.getBorrowUndispose();
    }
}
