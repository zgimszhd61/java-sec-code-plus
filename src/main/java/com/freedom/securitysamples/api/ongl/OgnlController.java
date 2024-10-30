package com.freedom.securitysamples.api.ongl;

import ognl.Ognl;
import ognl.OgnlException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OgnlController {
    @GetMapping("/ognl/evaluate")
    public String evaluateOgnlExpression(String ognlExpression) throws OgnlException {
        // String ognlExpression = "@com.github.fartherp.framework.common.util.Tools@executeShell('/System/Applications/Calculator.app/Contents/MacOS/Calculator')";
        // Ognl.getValue(Ognl.parseExpression(ognlExpression), null);
        Ognl.getValue(Ognl.parseExpression(ognlExpression), null);

        return "{'msg':'false'}";
    }
}