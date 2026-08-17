package com.dynatrace;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class ACastProblem {
    public static void main(String[] args) throws Exception {

        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()}, ClassLoader.getPlatformClassLoader());
        Class<?> otherAClass = classLoader.loadClass("com.dynatrace.A");


        A a = new A();

        Object otherA = otherAClass.getConstructor().newInstance();


        a.run();

        Method runMethod = otherAClass.getMethod("run");
        runMethod.invoke(otherA);

        Runnable r = a;
        r.run();
        r = (Runnable) otherA;
        r.run();

        a = (A) otherA;
        a.run();
    }

}