package com.freedom.javacodesimple.api.rce;


import cn.hutool.core.util.RuntimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;


//风险提醒：此处接口仅供测试使用，不要部署到生产环境(可以入侵服务器)
@RestController
@RequestMapping("/api")
public class RceController {

    //2023.06.23:返回值为success.
    @GetMapping("/rce/bad01")
    public String rce01(String cmd) throws IOException {
        java.lang.Runtime.getRuntime().exec(cmd);
        return "success";
    }

    //2023.06.23:返回值为命令执行结果.
    @GetMapping("/rce/bad02")
    public String rce02(String cmd) throws IOException {
        String str = RuntimeUtil.execForStr(cmd);

        return str;
    }

    @GetMapping("/rce/bad03")
    public String rce03(String payload) throws IOException {
        String[] cmds = new String[] {Arrays.toString(payload.split(" "))};
//        String[] cmds = new String[] {"open","-a","Calculator"};
        new ProcessBuilder(cmds).start();
        return "";
    }

}
