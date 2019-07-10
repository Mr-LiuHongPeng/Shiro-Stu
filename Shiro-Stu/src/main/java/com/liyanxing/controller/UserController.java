package com.liyanxing.controller;

import com.liyanxing.pojo.User;
import com.liyanxing.services.UserService;
import com.liyanxing.util.UserRegisteAndLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;

@Controller
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService service;

    /**
     * 处理用户的登录请求
     */
    @PostMapping("/userLogin")
    public String userLogin(User user, Model model) {
        user.setPassword(UserRegisteAndLogin.getInputPasswordCiph(user.getPassword(), service.selectAsaltByName(user.getName())));

        return UserRegisteAndLogin.userLogin(user, model);
    }

    /**
     * 处理用户的注册请求
     *
     * @param user
     * @return
     */
    @PostMapping("/userRegister")
    public String userRegister(User user, Model model) {
        String userName = user.getName();
        String password = user.getPassword();

        String[] saltAndCiphertext = UserRegisteAndLogin.encryptPassword(password);


        user.setSalt(saltAndCiphertext[0]);//盐
        user.setPassword(saltAndCiphertext[1]);//密文

        service.userRegister(user);

        model.addAttribute("msg","注册成功");
        return UserRegisteAndLogin.userLogin(user, model);
    }
}