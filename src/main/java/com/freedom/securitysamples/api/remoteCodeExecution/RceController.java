package com.freedom.securitysamples.api.remoteCodeExecution;

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
    public String executeCommand01(String command) throws IOException {
        java.lang.Runtime.getRuntime().exec(command);
        return "success";
    }

    //2023.06.23:返回值为命令执行结果.
    @GetMapping("/rce/bad02")
    public String executeCommand02(String command) throws IOException {
        String commandResult = RuntimeUtil.execForStr(command);
        return commandResult;
    }

    @GetMapping("/rce/bad03")
    public String executeCommand03(String commandInput) throws IOException {
        String[] commandArray = new String[] {Arrays.toString(commandInput.split(" "))};
//        String[] commandArray = new String[] {"open","-a","Calculator"};
        new ProcessBuilder(commandArray).start();
        return "";
    }

}