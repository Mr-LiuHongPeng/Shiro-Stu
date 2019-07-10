package com.liyanxing.services;

import com.liyanxing.mapper.UserMapper;
import com.liyanxing.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    /**
     * 查询所有用户的所有信息
     * @return
     */
    @Override
    public List<User> selectAllUser()
    {
        return userMapper.selectAllUser();
    }

    /**
     * 根据用户名查询一个用户的所有信息
     *
     * @param username
     * @return
     */
    @Override
    public User selectOneUserByName(String username)
    {
        return userMapper.selectOneUserByName(username);
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @Override
    public void userRegister(User user)
    {
        userMapper.userRegister(user);
    }

    /**
     * 根据用户名查询这个用户的盐值
     *
     * @param name
     * @return
     */
    @Override
    public String selectAsaltByName(String name)
    {
        return userMapper.selectAsaltByName(name);
    }
}