package com.freedom.securitysamples.api.logicVuln;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IpForgeController {
    @RequestMapping("/ipforge/good01")
    public String getClientIpAddress(HttpServletRequest request) {
        // 无法伪造
        return request.getRemoteAddr();
    }

    @RequestMapping("/ipforge/bad01")
    public String getIpFromXForwardedFor(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        // 可以伪造
        return clientIp;
    }

    @RequestMapping("/ipforge/bad02")
    public String getIpFromXRealIP(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Real-IP");
        // 可以伪造
        return clientIp;
    }
}