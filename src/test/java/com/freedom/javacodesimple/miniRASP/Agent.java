package com.freedom.javacodesimple.miniRASP;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

// 参考教程：https://www.perplexity.ai/search/JavaRASPtesthoo-world-AnfSQ5cITWaPsFBnKmAKDQ
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("RASP Agent is running");
        inst.addTransformer(new Transformer());
    }
}