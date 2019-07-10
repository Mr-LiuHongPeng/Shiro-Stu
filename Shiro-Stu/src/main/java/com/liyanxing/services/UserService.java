package com.liyanxing.services;

import com.liyanxing.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserService
{
    /**
     * 查询所有用户的所有信息
     * @return
     */
    List<User> selectAllUser();

    /**
     * 根据用户名查询一个用户的所有信息
     * @param username
     * @return
     */
    User selectOneUserByName(String username);

    /**
     * 用户注册
     * @param user
     */
    void userRegister(User user);

    /**
     * 根据用户名查询这个用户的盐值
     * @param name
     * @return
     */
    String selectAsaltByName(String name);
}