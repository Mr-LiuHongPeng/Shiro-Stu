#### <center>写在前边</center>
- 现在的时间：2018-1-8 (离数据库考试还有２天，我的心是真滴大。)
- 使用了加盐加密，如果你还不了解什么加盐加密的话，请点击：[加盐加密的简单了解](https://blog.csdn.net/Sacredness/article/details/86027143)

#### <center>所用技术</center>
- SpringBoot
- MyBatis
- Shiro
- Thymeleaf
- MySQL

#### <center>加密函数</center>
- 我相信你对shiro不加密的用户注册与加密已经有了了解。
- 如果是这样的话那么你只需要下面这个shiro提供的加密函数
```java
String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //生成盐值
String ciphertext = new Md5Hash(password,salt,3).toString(); //生成的密文，使用md5算法对明文与盐值的组合进行了三次加密
```

#### <center>代码</center>
- pojo
```java
package com.liyanxing.pojo;

public class User
{
    private Integer id;
    private String name;
    private String password;
    private String per;
    private String salt;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPer()
    {
        return per;
    }

    public void setPer(String per)
    {
        this.per = per;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", password='" + password + '\'' + ", per='" + per + '\'' + '}';
    }
}
```
- 处理用户注册与登录的函数
```java
package com.liyanxing.util;

import com.liyanxing.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;

/**
 *用户注册与登录时用到的函数
 */
public class UserRegisteAndLogin
{
    /**
     * 用户注册时加密用户的密码
     * 输入密码明文 返回密文与盐值
     * @param password
     * @return 第一个是密文  第二个是盐值
     */
    public static String[] encryptPassword(String password)
    {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //生成盐值
        String ciphertext = new Md5Hash(password,salt,3).toString(); //生成的密文

        String[] strings = new String[]{salt, ciphertext};

        return strings;
    }

    /**
     * 获得本次输入的密码的密文
     * @param password
     * @param salt
     * @return
     */
    public static String getInputPasswordCiph(String password, String salt)
    {
        if(salt == null)
        {
            password = "";
        }

        String ciphertext = new Md5Hash(password,salt,3).toString(); //生成的密文

        return ciphertext;
    }

    /**
     * 用户登录函数，在controller里调用
     * @param user
     * @param model
     * @return
     */
    public static String userLogin(User user, Model model)
    {
        Subject subject = SecurityUtils.getSubject(); //获得Subject对象
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword()); //将用户输入的用户名写密码封装到一个UsernamePasswordToken对象中

        //用Subject对象执行登录方法，没有抛出任何异常说明登录成功
        try
        {
            subject.login(token); //login()方法会调用 Realm类中执行认证逻辑的方法，并将这个参数传递给doGetAuthenticationInfo()方法
            model.addAttribute("user_name", user.getName());
            return "index";
        }
        catch (UnknownAccountException e) //抛出这个异常说明用户不存在
        {
            model.addAttribute("msg", "用户不存在");
            return "login";
        }
        catch (IncorrectCredentialsException e) //抛出这个异常说明密码错误
        {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}
```
- 处理注册与登录请求的控制器
```java
package com.liyanxing.controller;

import com.liyanxing.pojo.User;
import com.liyanxing.services.UserService;
import com.liyanxing.util.UserRegisteAndLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController
{
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService service;

    /**
     * 处理用户的登录请求
     */
    @PostMapping("/userLogin")
    public String userLogin(User user, Model model)
    {
        user.setPassword(UserRegisteAndLogin.getInputPasswordCiph(user.getPassword(), service.selectAsaltByName(user.getName())));

        return UserRegisteAndLogin.userLogin(user, model);
    }

    /**
     * 处理用户的注册请求
     * @param user
     * @return
     */
    @PostMapping("/userRegister")
    public String userRegister(User user, Model model)
    {
        String userName = user.getName();
        String password = user.getPassword();

        String[] saltAndCiphertext = UserRegisteAndLogin.encryptPassword(password);

        user.setSalt(saltAndCiphertext[0]);
        user.setPassword(saltAndCiphertext[1]);

        service.userRegister(user);

        return UserRegisteAndLogin.userLogin(user, model); //使用户沆注册后立马登录
    }
}
```
- realm类
```java
package com.liyanxing.shiro;

import com.liyanxing.pojo.User;
import com.liyanxing.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm
{
    @Autowired
    private UserService userService;

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken; //获得用户输入的用户名,这个对象就是login()传递过来的，将它强转以取出封装的用户名
        String userNameInput = token.getUsername();

        User selectUser = userService.selectOneUserByName(userNameInput);

        if(selectUser == null) //用户不存在，返回null
        {
            return null;
        }

        return new SimpleAuthenticationInfo(selectUser, selectUser.getPassword(), "");
    }

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        Subject subject = SecurityUtils.getSubject(); //获得一个Subject对象
        User user = (User) subject.getPrincipal(); //获得登录的对象

        String str = user.getPer(); //获得登录对象的权限字符串

        if(str==null || str.isEmpty())
        {
            str = "noAnyAuth";
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); //创建一个这样的对象，它是返回的类型的子类
        info.addStringPermission(str); //添加权限，这个方法的参数字符串如果为 null或"" 会抛出异常

        return info;
    }
}
```