package com.freedom.securitysamples.api.pathTraversal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// 路径遍历漏洞
@RestController
@RequestMapping("/api")
public class PathTraversalController {
    @RequestMapping("/path/bad01")
    public String readFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            String fileContent = new String(fileData);
            return fileContent;
        }
        return filePath;
    }
}