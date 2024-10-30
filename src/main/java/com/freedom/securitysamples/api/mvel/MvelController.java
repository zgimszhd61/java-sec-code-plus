package com.freedom.securitysamples.api.mvel;

import cn.hutool.extra.expression.engine.mvel.MvelEngine;
import ognl.OgnlException;
import org.mvel2.MVEL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MvelController {
    @GetMapping("/mvel/bad01")
    public String executeMvelWithPayload(String mvelPayload) throws OgnlException {
        MvelEngine mvelEngine = new MvelEngine();
//        mvelEngine.eval("Runtime.getRuntime().exec(\"open -a Calculator\")", null);
        mvelEngine.eval(mvelPayload, null);
        return  "{'msg':'false'}";
    }

    @GetMapping("/mvel/bad02")
    public String executeMvelDirectly(String mvelPayload) throws OgnlException {
        MVEL.eval(mvelPayload);
        return  "{'msg':'false'}";
    }
}