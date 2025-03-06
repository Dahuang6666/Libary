package com.lifeng.Mapper;


import com.lifeng.Pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {
    @Insert("INSERT INTO books (name, author, categoryId, count) " +
            "VALUES (#{name}, #{author}, #{categoryId}, #{count})")
    void addBook(Books book);

    @Delete("DELETE FROM books WHERE id = #{bookId}")
    void deleteBook(Integer id);

    @Update("UPDATE books " +
            "SET name = #{name}, author = #{author}, categoryId = #{categoryId}, count = #{count} " +
            "WHERE id = #{id}")
    int updateBook(Books book);

    @Select("select * from library.borrow_requests where status=0")
    List<BorrowRequests> getBorrowUndispose();
    @Select("select * from library.borrow_requests where status!=0")
    List<BorrowRequests> getBorrowDispose();
    @Update("update borrow_requests set status=#{status} where id=#{id}")
    void UpdateBorrow(BorrowRequests borrowRequests);

    List<bookDTO> searchBooksByConditions(Map<String, Object> map);
    @Select("select * from book_category")
    List<BookCategory> getAllCategories();
    @Insert("insert into book_category (name) values (#{name})")
    void addCategory(BookCategory category);
     @Delete("delete from book_category where id=#{id}")
    void deleteCategory(int id);
    @Update("update login set password=#{password} where id=#{id}")
    void resetPassword(Login login);
    @Update("update login set username=#{username},permissions=#{permissions},email=#{email} where id=#{id}")
    void updateLogin(Login login);
    @Delete("delete from login where id=#{id}")
    void deleteLogin(int id);
}
