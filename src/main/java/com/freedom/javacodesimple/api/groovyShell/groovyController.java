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
public class groovyController {
    @GetMapping("/groovy/bad01")
    public String groovy01(String payload) {
        GroovyScriptEvaluator gse = new GroovyScriptEvaluator();
        ScriptSource scriptSource = new StaticScriptSource(payload);//关键用户输入
//        ScriptSource scriptSource = new StaticScriptSource("\"open -a Calculator\".execute().text");//关键用户输入
        gse.evaluate(scriptSource);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad02")
    public String groovy02(String script) throws InstantiationException, IllegalAccessException {
        final GroovyClassLoader classLoader = new GroovyClassLoader();
        Class groovy = classLoader.parseClass(script);
        GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad03")
    public String groovy03(String script){
        Eval.me(script);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad04")
    public String groovy04(String script){
        GroovyShell shell = new GroovyShell();
        shell.evaluate(script);
        return  "{'msg':'false'}";
    }

    @GetMapping("/groovy/bad05")
    public String groovy05(String script){
        GroovyShell shell = new GroovyShell();
        GroovyCodeSource gcs = new GroovyCodeSource(script, "test", "Test");
        shell.evaluate(gcs);
        return  "{'msg':'false'}";
    }

}
