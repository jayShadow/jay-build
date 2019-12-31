package cn.jay.simple.security.controller;

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
    public String add() {
        return "/demo/add";
    }

    @GetMapping("/update")
    public String update() {
        return "/demo/update";
    }

}
