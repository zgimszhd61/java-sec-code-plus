package com.freedom.javacodesimple.miniIAST;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 定义一个接口
interface TestInterface {
    void test();
}

// 实现接口
class TestClass implements TestInterface {
    @Override
    public void test() {
        System.out.println("Inside test method");
    }
}

// 创建一个InvocationHandler来处理方法调用
class TestInvocationHandler implements InvocationHandler {
    private final Object target;

    public TestInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("test")) {
            System.out.println("hello world");
        }
        return method.invoke(target, args);
    }
}

public class Main {
    public static void main(String[] args) {
        // 创建实际对象
        TestClass testClass = new TestClass();

        // 创建代理对象
        TestInterface proxyInstance = (TestInterface) Proxy.newProxyInstance(
                testClass.getClass().getClassLoader(),
                testClass.getClass().getInterfaces(),
                new TestInvocationHandler(testClass)
        );

        // 调用代理对象的方法
        proxyInstance.test();
    }
}
