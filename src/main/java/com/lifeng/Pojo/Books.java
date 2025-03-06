package com.lifeng.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//书籍类
public class Books {
    Integer id; //唯一id
    String name; //书名
    String author; //作者
    Integer categoryId; //分类id
    Integer count; //库存数量
}
