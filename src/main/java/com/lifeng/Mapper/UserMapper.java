package com.lifeng.Mapper;


import com.lifeng.Pojo.BorrowRequests;
import com.lifeng.Pojo.bookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("SELECT b.id, b.name , b.author, b.count, c.name AS categoryName " +
            "FROM books b JOIN book_category c ON b.categoryId = c.id where b.count>0")
    List<bookDTO> getAllBooks();
    List<bookDTO> searchBooksByConditions(Map<String,Object> map);
    void addBorrower(BorrowRequests borrowRequests);
     @Select("select *from library.borrow_requests where userName=#{userName}")
     List<BorrowRequests> getBorrower(String userName);
     @Update("update library.login set avatarUrl=#{avatarUrl} where username=#{username}")
     void FileUpload(String username, String avatarUrl);
     @Select("select avatarUrl from library.login where username=#{username}")
     String getAvatarUrl(String username);
     @Update("update library.books set count=count-#{count} where id=#{id}")
     void reduceCount(Integer count,Integer id);
}
