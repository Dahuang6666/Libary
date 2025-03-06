package com.lifeng.Controller;
import java.util.UUID;
import com.lifeng.Pojo.BorrowRequests;
import com.lifeng.Pojo.Result;
import com.lifeng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
   private static final String defaultImg="http://localhost:8080/images/shu_jia.jpg";
    @Autowired
    private UserService userService;

    @GetMapping   //书籍查询
    public Result getAllBooks() {
        return Result.success(userService.getAllBooks());
    }
    @GetMapping("/search") //模糊查询
    public  Result  searchBooks(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String author,
                                  @RequestParam(required = false) String categoryName) {
        return  Result.success(userService.searchBooksByConditions(name, author, categoryName));
    }
    @GetMapping("/reduceCount")
    public Result reduceCount(@RequestParam  Integer count,@RequestParam Integer id) {

       if(userService.reduceCount(count,id))
       {
           return Result.success("True");
       }
       else{
           return Result.success("Falselll");
       }
       }
    //借书
    @PostMapping
    public void addBorrower(@RequestBody BorrowRequests borrowRequests) {
        userService.addBorrower(borrowRequests);
    }

    //查看借书进度
    @GetMapping("/borrow")
    public Result getBorrower(@RequestParam(required = false) String userName) {
        return Result.success(userService.getBorrower(userName));
    }
    //上传头像
    @PostMapping("/upload-avatar")
    public Result  uploadAvatar(@RequestParam String username, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.success("False");
        }
       else{
          userService.FileUpload(username, file);
        }
           return Result.success("True");
    }
    // 根据用户名获取图片地址
    @GetMapping("/avatar")
    public Result  getAvatarUrl(@RequestParam String username) {
        String avatarUrl = userService.getAvatarUrl(username);
        if(avatarUrl != null && !avatarUrl.isEmpty())
        {
           return  Result.success(userService.getAvatarUrl(username));
        }
        else{
            return  Result.success(defaultImg);
        }
    }


 }
