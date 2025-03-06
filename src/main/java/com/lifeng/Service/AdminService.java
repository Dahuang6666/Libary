package com.lifeng.Service;

import com.lifeng.Pojo.*;

import java.util.List;

public interface AdminService {
    void addBook(Books book);

    void deleteBook(Integer id);

    String updateBook(Books book);

    List<BorrowRequests> getBorrowUndispose();

    List<BorrowRequests> getBorrowDispose();

    void UpdateBorrow(BorrowRequests borrowRequests);

    List<bookDTO> searchBooksByConditions(String name, String author, String categoryName);

    List<BookCategory> getAllCategories();

    void addCategory(BookCategory category);

    void deleteCategory(int id);

    void resetPassword(Login login);

    void updateLogin(Login login);

    void deleteLogin(int id);
}
