package com.freedom.javacodesimple.api.bsh;

import ognl.OgnlException;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.bsh.BshScriptEvaluator;
import org.springframework.scripting.groovy.GroovyScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScriptExecutionController {
    @GetMapping("/bsh/evaluateScript")
    public String evaluateBshScript(String scriptPayload) throws OgnlException {
        BshScriptEvaluator scriptEvaluator = new BshScriptEvaluator();
//        String script = "java.lang.Runtime.getRuntime().exec(\"/System/Applications/Calculator.app/Contents/MacOS/Calculator\");";//ok
        scriptEvaluator.evaluate(new StaticScriptSource(scriptPayload));//重要.
        return  "{'msg':'false'}";
    }
}