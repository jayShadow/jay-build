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
 * @Date: 2020/1/14 17:34
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    @PreAuthorize("hasAuthority('add_user')")
    public LoginUser add(@AuthenticationPrincipal LoginUser loginUser, HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession();
        return loginUser;
    }

}
