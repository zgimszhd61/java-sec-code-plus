package com.freedom.javacodesimple.api.mvel;


import cn.hutool.extra.expression.engine.mvel.MvelEngine;
import ognl.Ognl;
import ognl.OgnlException;
import org.mvel2.MVEL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class mvelController {
    @GetMapping("/mvel/bad01")
    public String mvel01(String payload) throws OgnlException {
        MvelEngine me = new MvelEngine();
//        me.eval("Runtime.getRuntime().exec(\"open -a Calculator\")",null);
        me.eval(payload,null);
        return  "{'msg':'false'}";
    }

    @GetMapping("/mvel/bad02")
    public String mvel02(String payload) throws OgnlException {
        MVEL.eval(payload);
        return  "{'msg':'false'}";
    }
}
