package com.freedom.javacodesimple.api.xxe;

import org.jdom2.input.sax.SAXHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

@RestController
@RequestMapping("/api")
public class XxeController {


    //2023.06.23:返回值为success.
    //攻击Payload:"<!DOCTYPE doc [ \n" +
    //                    "<!ENTITY xxe SYSTEM \"http://127.0.0.1:1664\">\n" +
    //                    "]><doc>&xxe;</doc>"

    @GetMapping("/xxe/bad01")
    public String processXmlUsingDom(String xmlContent) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream xmlInputStream = new ByteArrayInputStream(xmlContent.getBytes());
        Document document = documentBuilder.parse(xmlInputStream);
        return "success";
    }

    @GetMapping("/xxe/bad03")
    public String processXmlUsingSax(String xmlContent) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        SAXHandler saxHandler = new SAXHandler();
        saxParser.parse(xmlContent, saxHandler);
        return "success";
    }


}