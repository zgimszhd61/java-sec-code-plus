package com.freedom.javacodesimple.api.groovyShell;

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
        GroovyScriptEvaluator scriptEvaluator = new GroovyScriptEvaluator();
        ScriptSource scriptSource = new StaticScriptSource(groovyScript); // 关键用户输入
//        ScriptSource scriptSource = new StaticScriptSource("\"open -a Calculator\".execute().text"); // 关键用户输入
        scriptEvaluator.evaluate(scriptSource);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad02")
    public String executeGroovyScriptWithClassLoader(String groovyScript) throws InstantiationException, IllegalAccessException {
        final GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class parsedClass = groovyClassLoader.parseClass(groovyScript);
        GroovyObject groovyObject = (GroovyObject) parsedClass.newInstance();
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad03")
    public String executeGroovyScriptWithEval(String groovyScript){
        Eval.me(groovyScript);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad04")
    public String executeGroovyScriptWithShell(String groovyScript){
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate(groovyScript);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad05")
    public String executeGroovyScriptWithCodeSource(String groovyScript){
        GroovyShell groovyShell = new GroovyShell();
        GroovyCodeSource codeSource = new GroovyCodeSource(groovyScript, "test", "Test");
        groovyShell.evaluate(codeSource);
        return  "{'msg':'false'}";
    }

}