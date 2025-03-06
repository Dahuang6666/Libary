package com.lifeng.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
//书籍类型类
public class BookCategory {
     Integer  id; //书籍类型id
     String  name; //书籍类型名称
}
