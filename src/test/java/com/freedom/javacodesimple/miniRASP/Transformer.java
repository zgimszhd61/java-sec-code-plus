package com.freedom.javacodesimple.miniRASP;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

// 参考教程：https://www.perplexity.ai/search/JavaRASPtesthoo-world-AnfSQ5cITWaPsFBnKmAKDQ

///Users/a0000/mywork/commonLLM/opensource/nnnew/javabenchmark/src/test/java/com/freedom/javacodesimple/miniRASP/TargetClass.java
public class Transformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (className.equals("test/java/com/freedom/javacodesimple/miniRASP/TargetClass")) {
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.get("com.freedom.javacodesimple.miniRASP.TargetClass");
                CtMethod ctMethod = ctClass.getDeclaredMethod("test");
                ctMethod.insertBefore("{ System.out.println(\"hello world\"); }");
                return ctClass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}