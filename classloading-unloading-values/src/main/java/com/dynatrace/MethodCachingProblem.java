package com.dynatrace;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class MethodCachingProblem {
    public static void main(String[] args) throws Exception {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()});

        Class<?> aClass = classLoader.loadClass("com.dynatrace.A");
        Object aObject = aClass.getConstructor().newInstance();
        Method runMethod = aClass.getMethod("run");
        runMethod.invoke(aObject);

        Reference<?> classLoaderWeakReference = new WeakReference<>(classLoader);
        Reference<?> methodWeakReference = new WeakReference<>(runMethod);

//        classLoader = null;
//        aClass = null;
//        aObject = null;

        runMethod = null;

        while (classLoaderWeakReference.get() != null && methodWeakReference.get() != null) {
            System.gc();
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println();
        System.out.println("wohooo");
        System.out.println(classLoaderWeakReference.get());
        System.out.println(methodWeakReference.get());
    }

}