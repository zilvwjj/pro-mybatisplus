package com.jwan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jwan.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {
    @Select("select * from tbl_user")
    List<User> selectAll();
}
