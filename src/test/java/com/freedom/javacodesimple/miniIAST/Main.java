package com.freedom.javacodesimple.miniIAST;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 定义一个接口
interface ActionInterface {
    void performAction();
}

// 实现接口
class ActionClass implements ActionInterface {
    @Override
    public void performAction() {
        System.out.println("Inside performAction method");
    }
}

// 创建一个InvocationHandler来处理方法调用
class ActionInvocationHandler implements InvocationHandler {
    private final Object originalObject;

    public ActionInvocationHandler(Object originalObject) {
        this.originalObject = originalObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] methodArguments) throws Throwable {
        if (method.getName().equals("performAction")) {
            System.out.println("hello world");
        }
        return method.invoke(originalObject, methodArguments);
    }
}

public class Main {
    public static void main(String[] args) {
        // 创建实际对象
        ActionClass actionClassInstance = new ActionClass();

        // 创建代理对象
        ActionInterface actionProxyInstance = (ActionInterface) Proxy.newProxyInstance(
                actionClassInstance.getClass().getClassLoader(),
                actionClassInstance.getClass().getInterfaces(),
                new ActionInvocationHandler(actionClassInstance)
        );

        // 调用代理对象的方法
        actionProxyInstance.performAction();
    }
}