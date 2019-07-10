package com.liyanxing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resouces")
public class Resouces {
    @GetMapping("/a")
    public String resA() {
        return "这是资源A aaaaaaaaaaa";
    }

    @GetMapping("/b")
    public String resB() {
        return "这是资源B bbbbbbbbbbb";
    }

    @GetMapping("/haveNoPer")
    public String haveNoPer() {
        return "你没有权限访问这个资源";
    }
}