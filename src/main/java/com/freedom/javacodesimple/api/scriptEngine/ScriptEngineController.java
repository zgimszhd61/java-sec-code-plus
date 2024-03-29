package com.freedom.javacodesimple.api.scriptEngine;

import org.openjdk.nashorn.api.scripting.NashornScriptEngine;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ScriptEngineController {
    @GetMapping("/scriptengine/bad01")
    public String scriptengine01(String payload) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        //        nashorn.eval("var x=new java.lang.ProcessBuilder; x.command(\"/System/Applications/Calculator.app/Contents/MacOS/Calculator\"); print(x.start());");
        nashorn.eval(payload);
        return "success";
    }

    @GetMapping("/scriptengine/bad02")
    public String scriptengine02(String payload) throws  ScriptException {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        NashornScriptEngine engine = (NashornScriptEngine) factory.getScriptEngine(new String[] { "-scripting"});
        Object result = engine.eval(payload);
        return "success";
    }
}
