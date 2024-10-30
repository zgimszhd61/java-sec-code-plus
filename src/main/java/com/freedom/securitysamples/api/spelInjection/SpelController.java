package com.freedom.securitysamples.api.spelInjection;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpelController {
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    @GetMapping("/spel/bad01")
    public String executeSpel01(String userInput) {
        SpelExpressionParser spelParser = new SpelExpressionParser();
        Expression parsedExpression = spelParser.parseExpression(userInput);
        String result = (String) parsedExpression.getValue();
        return "success";
    }

    @GetMapping("/spel/bad02")
    public String executeSpel02(String userInput) {
        SpelExpressionParser spelParser = new SpelExpressionParser();
        SpelExpression parsedExpression = spelParser.parseRaw(userInput);
        parsedExpression.getValue();
        return "success";
    }

    @GetMapping("/spel/bad03")
    public String executeSpel03(String userInput) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression parsedExpression = parser.parseExpression(userInput);
        parsedExpression.getValue(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad04")
    public String executeSpel04(String userInput) {
        Expression parsedExpression = new SpelExpressionParser().parseExpression(userInput);
        parsedExpression.getValue(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad05")
    public String executeSpel05(String userInput) {
        Expression parsedExpression = new SpelExpressionParser().parseExpression(userInput);

        Object targetObject = new Object();
        Object newValue = new Object();
        parsedExpression.setValue(targetObject, newValue); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad06")
    public String executeSpel06(String userInput) {
        Expression parsedExpression = expressionParser.parseExpression(userInput);
        parsedExpression.getValue(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad07")
    public String executeSpel07(String userInput) {
        Expression parsedExpression = expressionParser.parseExpression(userInput);
        parsedExpression.getValueType(); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/bad08")
    public String executeSpel08(String userInput) {
        Expression parsedExpression = expressionParser.parseExpression(userInput);

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        parsedExpression.getValue(evaluationContext); // $hasSpelInjection
        return "success";
    }

    @GetMapping("/spel/good01")
    public String executeSpel09(String userInput) {
        Expression parsedExpression = expressionParser.parseExpression(userInput);
        SimpleEvaluationContext evaluationContext = SimpleEvaluationContext.forReadWriteDataBinding().build();

        parsedExpression.getValue(evaluationContext); // Safe - the expression is evaluated in a limited context
        return "success";
    }
}