package com.lifeng.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    Integer id;
    String username;
    String email;
    Integer permissions;//权限 1 用户，0 管理员，6 超管
}
