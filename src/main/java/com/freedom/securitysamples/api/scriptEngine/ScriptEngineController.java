package com.freedom.securitysamples.api.scriptEngine;

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
    @GetMapping("/scriptengine/execute01")
    public String executeScriptWithEngineManager(String scriptPayload) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashornScriptEngine = scriptEngineManager.getEngineByName("nashorn");
        nashornScriptEngine.eval(scriptPayload);
        return "success";
    }

    @GetMapping("/scriptengine/execute02")
    public String executeScriptWithEngineFactory(String scriptPayload) throws ScriptException {
        NashornScriptEngineFactory scriptEngineFactory = new NashornScriptEngineFactory();
        NashornScriptEngine nashornScriptEngine = (NashornScriptEngine) scriptEngineFactory.getScriptEngine(new String[]{"-scripting"});
        Object scriptResult = nashornScriptEngine.eval(scriptPayload);
        return "success";
    }
}