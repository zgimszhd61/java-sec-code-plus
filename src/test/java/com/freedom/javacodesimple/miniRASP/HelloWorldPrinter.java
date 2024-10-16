package com.freedom.javacodesimple.miniRASP;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class HelloWorldPrinter {
    public void printHelloWorld() {
        System.out.println("Hello, World!");
    }

    public static void main(String[] args) {
        new HelloWorldPrinter().printHelloWorld();
    }
}