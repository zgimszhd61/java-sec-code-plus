package com.freedom.securitysamples.api.headerInjection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HeaderInjectionController {

    // 不安全的实现 - 存在 HTTP 头注入漏洞
    @GetMapping("/header/unsafe")
    public ResponseEntity<String> unsafeHeader(@RequestParam String userInput) {
        HttpHeaders headers = new HttpHeaders();

        // 直接将用户输入添加到响应头中，没有进行任何验证和过滤
        headers.add("X-Custom-Header", userInput);

        // 创建包含可能被注入的响应头的响应
        return ResponseEntity
                .ok()
                .headers(headers)
                .body("Response with potentially injected header");
    }

    // 另一个不安全的示例 - 允许用户控制 Location 头
    @GetMapping("/redirect/unsafe")
    public ResponseEntity<String> unsafeRedirect(@RequestParam String redirectUrl) {
        HttpHeaders headers = new HttpHeaders();

        // 直接使用用户输入作为重定向URL，没有验证
        headers.add("Location", redirectUrl);

        return ResponseEntity
                .status(302)
                .headers(headers)
                .body("Redirecting...");
    }

    // 不安全的 Cookie 设置示例
    @GetMapping("/cookie/unsafe")
    public ResponseEntity<String> unsafeCookie(@RequestParam String cookieValue) {
        HttpHeaders headers = new HttpHeaders();

        // 直接将用户输入添加到 Cookie 中，没有进行清理
        headers.add("Set-Cookie", "userdata=" + cookieValue);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body("Cookie set");
    }
}