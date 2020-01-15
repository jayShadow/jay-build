package cn.jay.security.controller;

import cn.jay.security.bean.LoginUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: Jay
 * @Date: 2019/12/24 16:41
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('add_user')")
    public String add(@AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return "#add_user# success =====by:" + loginUser.getUsername();
    }

    @GetMapping("/update")
//    @PreAuthorize("hasAuthority('update_user')")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        return "/demo/update";
    }

}
