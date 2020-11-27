package com.zyq.learnjwt.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyq.learnjwt.annotation.PassToken;
import com.zyq.learnjwt.annotation.UserLoginToken;
import com.zyq.learnjwt.entity.User;
import com.zyq.learnjwt.service.UserService;
import com.zyq.learnjwt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PassToken
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, Object> Login(@RequestBody User user){
        return userService.Login(user);
    }

    @PassToken
    @RequestMapping(value = "/registor",method = RequestMethod.POST)
    public Map<String,Object> Registor(@RequestBody User user){
        return userService.Registor(user);
    }
}
