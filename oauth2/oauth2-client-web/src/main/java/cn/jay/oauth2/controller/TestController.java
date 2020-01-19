package cn.jay.oauth2.controller;

import cn.jay.security.bean.SecurityUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: Jay
 * @Date: 2020/1/19 15:02
 */
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    @PreAuthorize("hasAuthority('add_user')")
    public SecurityUser add(@AuthenticationPrincipal SecurityUser loginUser, HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        return loginUser;
    }

}
