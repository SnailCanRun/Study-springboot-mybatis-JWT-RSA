package com.zyq.learnjwt.mapper;

import com.zyq.learnjwt.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User FindUserById(@Param("id") String id);

    User FindUserByEmail(String email);

    int Register(User user);
}
