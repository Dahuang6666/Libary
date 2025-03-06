package com.lifeng.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class bookDTO {
    private Integer id;
    private String  name;
    private String  author;
    private Integer count;  //剩余数量
    private String  categoryName;
}