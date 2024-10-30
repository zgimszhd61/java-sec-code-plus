package com.freedom.securitysamples.api.yamlDeserialization;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class YamlController {
    @GetMapping("/yaml/processPayload01")
    public String processYamlPayloadV1(String yamlPayload) throws IOException, ParserConfigurationException, SAXException {
//        String maliciousYaml = "!!javax.script.ScriptEngineManager [!!java.net.URLClassLoader "  + "[[!!java.net.URL [\"http://localhost\"]]]";
        Yaml yamlParser = new Yaml();
//        Object loadedObject = yamlParser.load(maliciousYaml);
        Object loadedObject = yamlParser.loadAs(yamlPayload, null);
        return "success";
    }

    @GetMapping("/yaml/processPayload02")
    public String processYamlPayloadV2(String yamlPayload) throws IOException, ParserConfigurationException, SAXException {
//        String maliciousYaml = "!!javax.script.ScriptEngineManager [!!java.net.URLClassLoader "  + "[[!!java.net.URL [\"http://localhost\"]]]";
        Yaml yamlParser = new Yaml();
        Object loadedObject = yamlParser.load(yamlPayload);
        return "success";
    }
}