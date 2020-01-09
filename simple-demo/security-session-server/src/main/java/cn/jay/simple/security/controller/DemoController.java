package cn.jay.simple.security.controller;

import cn.jay.simple.security.bean.LoginUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Jay
 * @Date: 2019/12/24 16:41
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('add_user')")
    public String add(@AuthenticationPrincipal LoginUser loginUser) {
        return "#add_user# success =====by:" + loginUser.getUsername();
    }

    @GetMapping("/update")
    @PreAuthorize("hasAuthority('update_user')")
    public String update() {
        return "/demo/update";
    }

}
