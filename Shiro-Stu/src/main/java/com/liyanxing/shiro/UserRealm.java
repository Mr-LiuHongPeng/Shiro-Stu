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

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获取到对应的token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken; //获得用户输入的用户名,这个对象就是login()传递过来的，将它强转以取出封装的用户名
        //从包装的token信息中取出登录的用户名
        String userName = token.getUsername();
        //获取密码
        String password = null;
        if (token.getPassword() != null) {
            password = new String(token.getPassword());
        }

        User selectUser = userService.selectOneUserByName(userName);

        if (selectUser == null)
        {
            return null;
        }

        return new SimpleAuthenticationInfo(selectUser, selectUser.getPassword(), "");
    }

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject(); //获得一个Subject对象
        User user = (User) subject.getPrincipal(); //获得登录的对象

        String str = user.getPer(); //获得登录对象的权限字符串

        if (str == null || str.isEmpty()) {
            str = "noAnyAuth";
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); //创建一个这样的对象，它是返回的类型的子类
        info.addStringPermission(str); //添加权限，这个方法的参数字符串如果为 null或"" 会抛出异常

        return info;
    }
}