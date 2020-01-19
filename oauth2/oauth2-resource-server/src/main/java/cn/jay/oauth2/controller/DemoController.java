package cn.jay.oauth2.controller;

import cn.jay.security.bean.LoginUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Jay
 * @Date: 2020/1/14 16:52
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('add_user')")
    public String add(@AuthenticationPrincipal String loginUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        return "#add_user# success =====by:";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('delete_user')")
    public String update() {
        return "/demo/delete";
    }

    @GetMapping("/update")
    @PreAuthorize("hasAuthority('add_user')")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

}
