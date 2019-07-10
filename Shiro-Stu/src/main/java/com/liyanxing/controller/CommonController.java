package com.liyanxing.controller;

import com.liyanxing.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommonController
{
    /**
     * 设置首页
     * @return
     */
    @GetMapping("/")
    public String indexPage(Model model)
    {
        try
        {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();

            model.addAttribute("user_name",user.getName());
        }
        catch (NullPointerException e)
        {
            //空指针异常，没有用户登录
        }


        return "index";
    }

    @GetMapping("/toPage/{page}")
    public String pageJump(@PathVariable(name = "page") String page)
    {
        return page;
    }
}