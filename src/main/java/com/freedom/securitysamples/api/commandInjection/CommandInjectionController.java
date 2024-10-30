package com.freedom.securitysamples.api.commandInjection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api")
public class CommandInjectionController {

    @GetMapping("/command/bad01")
    public String executeCommand(@RequestParam String command) {
        StringBuilder output = new StringBuilder();

        try {
            // 不安全的命令执行，直接使用用户输入
            Process process = Runtime.getRuntime().exec(command);

            // 读取命令执行结果
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            reader.close();

            return output.toString();

        } catch (Exception e) {
            return "命令执行错误: " + e.getMessage();
        }
    }
}