package com.zyq.learnjwt.service;

import com.zyq.learnjwt.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    Map<String,Object> Login(User user);

    User FindUserByEmail(String email);

    Map<String,Object> Registor(User user);
}
