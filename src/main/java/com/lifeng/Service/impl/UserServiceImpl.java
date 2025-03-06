package com.lifeng.Service.impl;
import com.lifeng.Mapper.UserMapper;
import com.lifeng.Pojo.Books;
import com.lifeng.Pojo.BorrowRequests;
import com.lifeng.Pojo.bookDTO;
import com.lifeng.Service.UserService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<bookDTO> getAllBooks() {
         return userMapper.getAllBooks();
    }

    @Override
    public List<bookDTO> searchBooksByConditions(String name, String author, String categoryName) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("author", author);
        map.put("categoryName", categoryName);
        return userMapper.searchBooksByConditions(map);
    }

    @Override
    public void addBorrower(BorrowRequests borrowRequests) {
        borrowRequests.setStatus(0);
        borrowRequests .setRequestTime(LocalDateTime.now());
        userMapper.addBorrower(borrowRequests);
    }

    @Override
    public List<BorrowRequests> getBorrower(String userName) {
        return userMapper.getBorrower(userName);
    }

    @Override
    public void FileUpload(String username, MultipartFile file) {
        // 获取原始文件名
        String originalFileName = file.getOriginalFilename();

        // 获取文件扩展名
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 生成随机文件名
        String randomFileName = UUID.randomUUID().toString() + fileExtension;
        // 拼接文件保存路径
        String userDir = System.getProperty("user.dir");
        File projectRoot = new File(userDir);
        System.out.println(userDir);
        String filePath = projectRoot + "/frontend/vue-project/src/assets/img/" + randomFileName;

        // 拼接文件保存路径

        String avatarUrl;
        try {
            // 保存文件
            file.transferTo(new File(filePath));
            // 生成图片访问 URL
            avatarUrl = "http://localhost:8080/images/" + randomFileName;

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // 更新用户头像地址
        userMapper.FileUpload(username, avatarUrl);
    }

    @Override
    public String getAvatarUrl(String username) {
       return  userMapper.getAvatarUrl(username);
    }

    @Override
    public boolean  reduceCount(Integer count,Integer id) {
           try{
               userMapper.reduceCount(count,id);
               return true;
           }
           catch (Exception e){
               return false;
           }

    }
}
