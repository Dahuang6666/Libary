package com.lifeng.Service;

import com.lifeng.Pojo.Books;
import com.lifeng.Pojo.BorrowRequests;
import com.lifeng.Pojo.bookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<bookDTO> getAllBooks();

    List<bookDTO> searchBooksByConditions(String name, String author, String categoryName);

    void addBorrower(BorrowRequests borrowRequests);

    Object getBorrower(String userName);

    void FileUpload(String username, MultipartFile file);
    String  getAvatarUrl(String username);

    boolean  reduceCount(Integer count,Integer id);
}
