package com.freedom.securitysamples.api.jsonp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api")
public class JsonpController {


    //2023.06.25:返回值为success.
    @GetMapping("/jsonp/bad01")
    public String jsonp01(String callback){
        return callback + "{'msg':'success'}";
    }
}
