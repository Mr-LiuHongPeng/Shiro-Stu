package com.liyanxing.controller.test;

import com.liyanxing.pojo.User;
import com.liyanxing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController
{
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @GetMapping("/test")
    public String test()
    {
        return userService.selectAsaltByName("Toney");
    }

    /**
     * 查询所有用户的所有信息
     * @return
     */
    @GetMapping("/selectAllUser")
    public List<User> selectAllUser()
    {
        return userService.selectAllUser();
    }

    /**
     * 根据用户名查询一个用户的所有信息
     * @param username
     * @return
     */
    @GetMapping("/selectOneUserByName")
    public User selectOneUserByName(@RequestParam(name = "name", defaultValue = "") String username)
    {
        return userService.selectOneUserByName(username);
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @PostMapping("/testUserRe")
    @ResponseBody
    public String userRegister(User user)
    {
        System.out.println(user.toString());

        if(user.getSalt() == null)
        {
            user.setSalt("");
        }
        userService.userRegister(user);

        return user.toString();
    }
}