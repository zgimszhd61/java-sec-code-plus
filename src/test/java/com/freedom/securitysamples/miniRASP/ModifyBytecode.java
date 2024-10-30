package com.freedom.securitysamples.miniRASP;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class ModifyBytecode {
    public static void main(String[] args) throws Exception {
        // 获取默认的ClassPool
        ClassPool classPool = ClassPool.getDefault();

        // 获取要修改的类
        CtClass targetClass = classPool.get("com.freedom.javacodesimple.miniRASP.onTestprint");

        // 获取类中的所有方法
        for (CtMethod method : targetClass.getDeclaredMethods()) {
            // 修改方法体
            method.instrument(new ExprEditor() {
                @Override
                public void edit(MethodCall methodCall) throws CannotCompileException {
                    // 检查是否是System.out.println()调用
                    if (methodCall.getClassName().equals("java.io.PrintStream") && methodCall.getMethodName().equals("println")) {
                        // 修改调用，添加前缀"DEBUG:"
                        methodCall.replace("{ $1 = \"DEBUG: \" + $1; $_ = $proceed($$); }");
                    }
                }
            });
        }

        // 将修改后的类写回文件
        targetClass.writeFile("output_directory");
    }
}