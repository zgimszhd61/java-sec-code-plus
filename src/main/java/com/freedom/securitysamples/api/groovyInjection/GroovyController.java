package com.freedom.securitysamples.api.groovyInjection;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.Eval;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.groovy.GroovyScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GroovyController {

    @GetMapping("/groovy/bad01")
    public String executeGroovyScriptWithEvaluator(String groovyScript) {
        // 使用 GroovyScriptEvaluator 执行脚本（潜在风险：代码注入）
        GroovyScriptEvaluator scriptEvaluator = new GroovyScriptEvaluator();
        ScriptSource scriptSource = new StaticScriptSource(groovyScript); // 注意用户输入的脚本内容
        scriptEvaluator.evaluate(scriptSource);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad02")
    public String executeGroovyScriptWithClassLoader(String groovyScript) throws InstantiationException, IllegalAccessException {
        // 使用 GroovyClassLoader 加载并执行脚本类（潜在风险：代码注入）
        final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class<?> parsedClass = groovyClassLoader.parseClass(groovyScript);
        GroovyObject groovyObject = (GroovyObject) parsedClass.newInstance();
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad03")
    public String executeGroovyScriptWithEval(String groovyScript){
        // 使用 Eval 执行脚本（潜在风险：代码注入）
        Eval.me(groovyScript);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad04")
    public String executeGroovyScriptWithShell(String groovyScript){
        // 使用 GroovyShell 执行脚本（潜在风险：代码注入）
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate(groovyScript);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad05")
    public String executeGroovyScriptWithCodeSource(String groovyScript){
        // 使用 GroovyCodeSource 执行脚本（潜在风险：代码注入）
        GroovyShell groovyShell = new GroovyShell();
        GroovyCodeSource codeSource = new GroovyCodeSource(groovyScript, "test", "Test");
        groovyShell.evaluate(codeSource);
        return  "{'msg':'false'}";
    }
}
