package com.lifeng.Controller;

import com.lifeng.Pojo.*;
import com.lifeng.Service.AdminService;
import com.lifeng.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
   @Autowired
   private AdminService adminService;
   @Autowired
   private LoginService loginService;
    @GetMapping("/search") //模糊查询
    public  Result  searchBooks(@RequestParam(required = false) String name,
                                @RequestParam(required = false) String author,
                                @RequestParam(required = false) String categoryName) {
        return  Result.success(adminService.searchBooksByConditions(name, author, categoryName));
    }
   //获取分类
   @GetMapping("/categories")
   public Result getAllCategories() {
       return  Result.success(adminService.getAllCategories());
   }
    //添加分类
    @PostMapping("/category")
    public void addCategory(@RequestBody BookCategory category) {
        adminService.addCategory(category);
    }
    //删除分类
   @DeleteMapping("/category/{id}")
   public void deleteCategory(@PathVariable int id) {
       adminService.deleteCategory(id);
   }
    //添加书籍
    @PostMapping
    public void addBook(@RequestBody Books book) {
        adminService.addBook(book);
    }
    //删除书籍
   @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable("id") Integer id) {
       adminService.deleteBook(id);
   }
   //更新书籍
    @PutMapping()
    public Result  updateBook(@RequestBody Books book) {
        return  Result.success(adminService.updateBook(book));
    }
    //查询账号(超管)
    @GetMapping
    public Result getLogin() {
        return Result.success(loginService.getLogin());
    }
    //重置密码
    @PostMapping("/password")
    public void resetPassword(@RequestBody Login login) {
        adminService.resetPassword(login);
    }
    //修改账户
    @PutMapping("/update")
    public void updateLogin(@RequestBody Login login) {
        adminService.updateLogin(login);
    }
    //删除账户
    @DeleteMapping("/deleteLogin/{id}")
    public void deleteLogin(@PathVariable int id) {
        adminService.deleteLogin(id);
    }
    //处理借书需求
    @PostMapping("/borrow")
    public void UpdateBorrow(@RequestBody BorrowRequests borrowRequests) {
        adminService.UpdateBorrow(borrowRequests);
    }
    //查看借书消息(未处理)
    @GetMapping("/borrow/undisposed")
    public Result getBorrowUndispose() {
        return Result.success(adminService.getBorrowUndispose());
    }
    //查看借书消息(已处理)
    @GetMapping("/borrow/dispose")
    public Result getBorrowDispose() {
        return Result.success(adminService.getBorrowDispose());
    }
}
