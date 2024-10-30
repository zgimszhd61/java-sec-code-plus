package com.freedom.securitysamples.api.securityHeaderMissing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecurityHeaderController {

    @GetMapping("/headers/unsafe")
    public String unsafeResponse() {
        // 直接返回响应,没有设置任何安全响应头
        return "{'status':'success','data':'sensitive information'}";
    }

    /*
    这段代码存在以下安全隐患:
    1. 没有设置 X-Content-Type-Options 头,可能导致MIME类型嗅探攻击
    2. 没有设置 X-Frame-Options 头,存在点击劫持风险
    3. 没有设置 X-XSS-Protection 头,容易遭受XSS攻击
    4. 没有设置 Strict-Transport-Security 头,存在降级攻击风险
    5. 没有设置 Content-Security-Policy 头,缺乏内容安全策略
    */
}