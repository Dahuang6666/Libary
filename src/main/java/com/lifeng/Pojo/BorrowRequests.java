package com.lifeng.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//借阅类
public class BorrowRequests {
    Integer id;
    String  userName;
    String  bookName;
    Integer   status;
    LocalDateTime requestTime;
    String message;
}
