package com.jwan.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    @TableField(value = "pwd",select = false)
    private String password;
    private Integer age;
    private String tel;
    @TableField(exist = false)
    private String address;

    private Integer deleted;
    @Version
    private Integer version;


    public User(Long id, String name, String password, Integer age, String tel) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.tel = tel;
    }
}