package com.freedom.javacodesimple.miniRASP;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class Transformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader classLoader, String fullyQualifiedClassName, Class<?> redefinedClass, ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        if (fullyQualifiedClassName.equals("com/freedom/javacodesimple/miniRASP/TargetClass")) {
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass targetClass = classPool.get("com.freedom.javacodesimple.miniRASP.TargetClass");
                CtMethod targetMethod = targetClass.getDeclaredMethod("test");
                targetMethod.insertBefore("{ System.out.println(\"hello world\"); }");
                return targetClass.toBytecode();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}