package com.liyanxing.mapper;

import com.liyanxing.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
@Mapper
public interface UserMapper
{
    /**
     * 查询所有用户的所有信息
     * @return
     */
    @Select("select * from user")
    List<User> selectAllUser();

    /**
     * 根据用户名查询一个用户的所有信息
     * @param username
     * @return
     */
    @Select("select * from user where name=#{username}")
    User selectOneUserByName(String username);

    /**
     * 用户注册
     * @param user
     */
    @Insert("insert into user (`name`,`password`,`salt`) values (#{name},#{password},#{salt})")
    void userRegister(User user);

    /**
     * 根据用户名查询这个用户的盐值
     * @param name
     * @return
     */
    @Select("select `salt` from user where `name`=#{name}")
    String selectAsaltByName(String name);
}