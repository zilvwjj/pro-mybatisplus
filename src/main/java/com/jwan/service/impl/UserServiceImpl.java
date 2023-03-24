package com.jwan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwan.dao.UserDao;
import com.jwan.domain.User;
import com.jwan.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
