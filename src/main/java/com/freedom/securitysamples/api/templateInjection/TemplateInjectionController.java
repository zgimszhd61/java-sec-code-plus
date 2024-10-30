package com.freedom.securitysamples.api.templateInjection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TemplateInjectionController {

    @GetMapping("/template/bad01")
    public String processTemplate(@RequestParam String userInput) {
        try {
            // 创建 FreeMarker 配置
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

            // 直接使用用户输入作为模板内容（这是不安全的！）
            Template template = new Template("userTemplate",
                    new StringReader(userInput),
                    cfg);

            // 准备数据模型
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("user", "admin");
            dataModel.put("role", "administrator");

            // 处理模板
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);

            return writer.toString();

        } catch (IOException | TemplateException e) {
            return "Template processing error: " + e.getMessage();
        }
    }
}