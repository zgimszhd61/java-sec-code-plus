package com.freedom.securitysamples.api.xpathInjection;

import org.springframework.web.bind.annotation.*;
import javax.xml.xpath.*;
import org.xml.sax.InputSource;
import java.io.StringReader;
import org.jaxen.dom.DOMXPath;
import org.jaxen.JaxenException;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

@RestController
@RequestMapping("/api/xpath")
public class XPathController {

    // 单一入口，处理传入的XPath表达式并返回结果
    @GetMapping("/evaluate")
    public String evaluateXPath(@RequestParam("expression") String expression,
                                @RequestParam("xml") String xmlContent) {
        try {
            // 创建XPath工厂和XPath实例
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();

            // 使用用户提供的XPath表达式和XML内容
            XPathExpression xPathExpression = xPath.compile(expression);
            InputSource inputSource = new InputSource(new StringReader(xmlContent));

            // 计算表达式并返回结果
            String result = xPathExpression.evaluate(inputSource);
            return "{'result':'" + result + "'}";
        } catch (XPathExpressionException e) {
            // 处理可能的异常
            return "{'error':'Invalid XPath expression or XML content'}";
        }
    }

    // 使用Jaxen库处理XPath表达式
    @GetMapping("/evaluateWithJaxen")
    public String evaluateXPathWithJaxen(@RequestParam("expression") String expression,
                                         @RequestParam("xml") String xmlContent) {
        try {
            // 解析XML内容
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlContent)));

            // 使用Jaxen解析XPath表达式
            DOMXPath domXPath = new DOMXPath(expression);
            List<?> results = domXPath.selectNodes(document);

            // 将结果转换为字符串
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("{'results': [");
            for (Object result : results) {
                resultBuilder.append("'" + result.toString() + "',");
            }
            if (!results.isEmpty()) {
                resultBuilder.setLength(resultBuilder.length() - 1); // 移除最后一个逗号
            }
            resultBuilder.append("]}");

            return resultBuilder.toString();
        } catch (JaxenException e) {
            return "{'error':'Invalid XPath expression'}";
        } catch (Exception e) {
            return "{'error':'Invalid XML content or parsing error'}";
        }
    }
}