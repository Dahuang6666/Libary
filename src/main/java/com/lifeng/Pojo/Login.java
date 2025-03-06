package com.lifeng.Pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//登录类
public class Login {
    Integer id;
    String username;
    String password;
    LocalDateTime createTime;
    String email;
    Integer permissions;//权限 1 用户，0 管理员，6 超管
    String avatarUrl;//图片地址
}
