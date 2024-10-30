package com.freedom.securitysamples.api.insecureDirectObjectReference;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insecureDirectObjectReference")
public class UserProfileController {

    @GetMapping("/user/profile")
    public String getUserProfile(@RequestParam Integer userId) {
        // 不安全的实现：直接根据用户ID返回信息，没有进行权限验证
        // 任何用户都可以通过修改userId参数查看其他用户的信息

        if (userId == 1) {
            return "{'userId': 1, 'username': 'admin', 'email': 'admin@example.com', 'phone': '13800138000'}";
        } else if (userId == 2) {
            return "{'userId': 2, 'username': 'test', 'email': 'test@example.com', 'phone': '13900139000'}";
        }

        return "{'error': 'User not found'}";
    }
}