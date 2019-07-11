package com.liyanxing.services;

import com.liyanxing.pojo.User;

/**
 * @author xiaoliu
 * @Date 2019/7/10 16:46
 */
public interface LoginService {

    /*登录*/
    User login(String userName, String password);
}
