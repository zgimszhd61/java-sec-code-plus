package com.freedom.javacodesimple.miniRASP;

public class DemoClass {
    public void displayMessage() {
        System.out.println("Inside displayMessage method");
    }

    public static void main(String[] args) {
        DemoClass demoInstance = new DemoClass();
        demoInstance.displayMessage();
    }
}