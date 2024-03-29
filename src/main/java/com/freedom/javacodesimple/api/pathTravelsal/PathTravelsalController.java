package com.freedom.javacodesimple.api.pathTravelsal;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// 路径遍历漏洞
@RestController
@RequestMapping("/api")
public class PathTravelsalController {
    @RequestMapping("/path/bad01")
    public String pathbad01(String filepath) throws IOException {
        File f = new File(filepath);
        if (f.exists()) {
            byte[] data = Files.readAllBytes(Paths.get(filepath));
            String str = new String(data);
            return str;
        }
        return filepath;
    }
}
