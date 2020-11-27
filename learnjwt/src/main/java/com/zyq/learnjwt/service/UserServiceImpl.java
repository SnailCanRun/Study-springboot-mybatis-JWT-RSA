package com.zyq.learnjwt.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zyq.learnjwt.entity.User;
import com.zyq.learnjwt.mapper.UserMapper;
import com.zyq.learnjwt.utils.JWTUtils;
import com.zyq.learnjwt.utils.RSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required=false)
    private UserMapper userMapper;

    @Override
    public Map<String,Object> Login(User user) {
        Map<String,Object> result = new HashMap<>();
        if("".equalsIgnoreCase(user.getId()) || user.getId() == null || user == null){
            result.put("state",false);
            result.put("msg","登录失败");
        }else {
            try {
                User userDB = this.FindUserByEmail(user.getEmail());
                String userPW = RSA.priDecrypt(userDB.getPassword());
                if(!user.getPassword().equalsIgnoreCase(userPW)){
                    result.put("state",false);
                    result.put("msg","用户名密码错误！");
                }else {
                    Map<String,String> payload = new HashMap<>();
                    payload.put("id",userDB.getId());
                    payload.put("name",userDB.getName());
                    payload.put("email",userDB.getEmail());
                    payload.put("sex",userDB.getSex());
                    String token = JWTUtils.getToken(payload);

                    result.put("state",true);
                    result.put("token",token);
                    result.put("msg","登录成功！");
                }
            }catch (Exception e){
                result.put("state",false);
                result.put("msg",e.getMessage());
            }
        }
        return result;
    }

    @Override
    public User FindUserByEmail(String email) {
        return userMapper.FindUserByEmail(email);
    }

    @Override
    public Map<String,Object> Registor(User user) {
        Map<String,Object> map = new HashMap<>();
        if(null == this.FindUserByEmail(user.getEmail())){
            try {
                user.setPassword(RSA.pubEncrypt(user.getPassword()));
                int result = userMapper.Register(user);
                if(result == 0){
                    map.put("state",false);
                    map.put("msg", "注册失败，请稍后重新注册");
                } else {
                    map.put("state",true);
                    map.put("msg", "注册成功");
                }
            } catch (Exception e) {
                map.put("state",false);
                map.put("msg", e.getMessage());
            }
        } else {
            map.put("state",false);
            map.put("msg", "该邮箱已注册");
        }
        return map;
    }
}
