package com.freedom.javacodesimple.api.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class SpelController {
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    @GetMapping("/spel/bad01")
    public String spel01(String cmd) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression expression = spelExpressionParser.parseExpression(cmd);
        String out = (String) expression.getValue();
        return "success";
    }

    @GetMapping("/spel/bad02")
    public String spel02(String cmd) {
//        String script = "T(java.lang.Runtime).getRuntime().exec('open -a Calculator')";
        SpelExpressionParser parser = new SpelExpressionParser();
        SpelExpression expr = parser.parseRaw(cmd);
        expr.getValue();
        return "success";
    }

    @GetMapping("/spel/bad03")
    public String spel03(String cmd) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(cmd);
        expression.getValue(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad04")
    public String spel04(String cmd) {
        Expression expression = new SpelExpressionParser().parseExpression(cmd);
        expression.getValue(); // $hasSpelInjection
        return "success";
    }
    @GetMapping("/spel/bad05")
    public String spel05(String cmd) {
        Expression expression = new SpelExpressionParser().parseExpression(cmd);

        Object root = new Object();
        Object value = new Object();
        expression.setValue(root, value); // $hasSpelInjection
        return "success";
    }
    @GetMapping("/spel/bad06")
    public String spel06(String cmd) {
        Expression expression = PARSER.parseExpression(cmd);
        expression.getValue(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad07")
    public String spel07(String cmd) {
        Expression expression = PARSER.parseExpression(cmd);
        expression.getValueType(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad08")
    public String spel08(String cmd) {
        Expression expression = PARSER.parseExpression(cmd);

        StandardEvaluationContext context = new StandardEvaluationContext();
        expression.getValue(context); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/good01")
    public String spel09(String cmd) {
        Expression expression = PARSER.parseExpression(cmd);
        SimpleEvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();

        expression.getValue(context); // Safe - the expression is evaluated in a limited context
        return "success";
    }
}
