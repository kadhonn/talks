package com.dynatrace;

import java.io.File;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class ClassValues {
    public static void main(String[] args) throws Exception {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()});

        Class<?> aClass = classLoader.loadClass("com.dynatrace.A");
        Object aObject = aClass.getConstructor().newInstance();

        ClassValue<Method> classValue = new ClassValue<>() {
            @Override
            protected Method computeValue(Class<?> type) {
                try {
                    return type.getMethod("run");
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Reference<?> methodWeakReference = new WeakReference<>(classValue.get(aClass));

        Reference<?> classLoaderWeakReference = new WeakReference<>(classLoader);
        Reference<?> classWeakReference = new WeakReference<>(aClass);
        Reference<?> objectWeakReference = new WeakReference<>(aObject);

//        classLoader = null;
//        aClass = null;
//        aObject = null;

        while (classLoaderWeakReference.get() != null && methodWeakReference.get() != null) {
            System.gc();
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println();
        System.out.println("wohooo");
        System.out.println(classLoaderWeakReference.get());
        System.out.println(classWeakReference.get());
        System.out.println(methodWeakReference.get());
        System.out.println(objectWeakReference.get());
        System.out.println(classValue);
    }

}