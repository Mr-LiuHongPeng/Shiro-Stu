package com.liyanxing.util;

import com.liyanxing.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 用户注册与登录时用到的函数
 */
public class UserRegisteAndLogin {

    /**
     * 用户注册时加密用户的密码
     * 输入密码明文 返回密文与盐值
     *
     * @param password
     * @return 第一个是密文  第二个是盐值
     */
    public static String[] encryptPassword(String password) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //生成盐值
        String ciphertext = new Md5Hash(password, salt, 1024).toString(); //生成的密文

        String[] strings = new String[]{salt, ciphertext};

        return strings;
    }

    /**
     * 获得本次输入的密码的密文
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getInputPasswordCiph(String password, String salt) {
        if (salt == null) {
            password = "";
        }

        String ciphertext = new Md5Hash(password, salt, 3).toString(); //生成的密文

        return ciphertext;
    }

    /**
     * 用户登录函数，在controller里调用
     *
     * @param user
     * @param model
     * @return
     */
    public static String userLogin(User user, Model model) {

        Subject subject = SecurityUtils.getSubject(); //获得Subject对象
        Session session = subject.getSession();

        /*Object user1 = session.getAttribute("user");
        Collection<Object> attributeKeys = session.getAttributeKeys();
        String host = session.getHost();
        Serializable id = session.getId();
        Date lastAccessTime = session.getLastAccessTime();
        Date startTimestamp = session.getStartTimestamp();
        long timeout = session.getTimeout();
        session.setTimeout(123L);
        Object username = session.removeAttribute("username");
        session.setAttribute("password","123");
        session.stop();
        session.touch();*/

        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword()); //将用户输入的用户名写密码封装到一个UsernamePasswordToken对象中

        //用Subject对象执行登录方法，没有抛出任何异常说明登录成功
        try {
            subject.login(token); //login()方法会调用 Realm类中执行认证逻辑的方法，并将这个参数传递给doGetAuthenticationInfo()方法
            model.addAttribute("user_name", user.getName());
            return "index";
        } catch (UnknownAccountException e) //抛出这个异常说明用户不存在
        {
            model.addAttribute("msg", "用户不存在");
            return "login";
        } catch (IncorrectCredentialsException e) //抛出这个异常说明密码错误
        {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}