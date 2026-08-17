package com.dynatrace;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class ClassUnloading {
    public static void main(String[] args) throws Exception {
        long count = 0;
        Set<Long> objectSet = new HashSet<>();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()});

        Class<?> aClass = classLoader.loadClass("com.dynatrace.A");
        Object aObject = aClass.getConstructor().newInstance();
        Method runMethod = aClass.getMethod("run");
        runMethod.invoke(aObject);

        Reference<?> classLoaderWeakReference = new WeakReference<>(classLoader);
        Reference<?> classWeakReference = new WeakReference<>(aClass);
        Reference<?> methodWeakReference = new WeakReference<>(runMethod);
        Reference<?> objectWeakReference = new WeakReference<>(aObject);
//        Reference<?> objectWeakReference = new SoftReference<>(aObject);

        classLoader = null;
        aClass = null;
        runMethod = null;
        aObject = null;

        while (classLoaderWeakReference.get() != null) {
            System.gc();
            System.out.print(".");
//            for (int i = 0; i < 100000; i++) {
//                objectSet.add(count++);
//            }
            Thread.sleep(200);
        }
        System.out.println();
        System.out.println("wohooo");
        System.out.println(objectSet.size());
        System.out.println(classLoaderWeakReference.get());
        System.out.println(classWeakReference.get());
        System.out.println(methodWeakReference.get());
        System.out.println(objectWeakReference.get());
    }

}