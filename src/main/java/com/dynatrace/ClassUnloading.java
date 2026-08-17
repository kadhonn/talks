package com.dynatrace;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassUnloading {
    public static void main(String[] args) throws Exception {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()});

        Class<?> aClass = classLoader.loadClass("com.dynatrace.A");
        Object aObject = aClass.getConstructor().newInstance();

        Reference<?> classLoaderWeakReference = new WeakReference<>(classLoader);
        Reference<?> classWeakReference = new WeakReference<>(aClass);
        Reference<?> objectWeakReference = new WeakReference<>(aObject);

        classLoader = null;
        aClass = null;
        aObject = null;

        while (classLoaderWeakReference.get() != null) {
            System.gc();
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println();
        System.out.println("wohooo");
        System.out.println(classLoaderWeakReference.get());
        System.out.println(classWeakReference.get());
        System.out.println(objectWeakReference.get());
    }

}