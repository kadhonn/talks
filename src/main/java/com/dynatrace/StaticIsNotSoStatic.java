package com.dynatrace;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class StaticIsNotSoStatic {
    public static void main(String[] args) throws Exception {

        URLClassLoader classLoader1 = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()}, ClassLoader.getPlatformClassLoader());
        Class<?> aClass1 = classLoader1.loadClass("com.dynatrace.A");
        Field rndField1 = aClass1.getField("RND");
        System.out.println(rndField1.get(null));

        URLClassLoader classLoader2 = new URLClassLoader(new URL[]{new File("./subsystem/build/libs/subsystem-1.0-SNAPSHOT.jar").toURI().toURL()}, ClassLoader.getPlatformClassLoader());
        Class<?> aClass2 = classLoader2.loadClass("com.dynatrace.A");
        Field rndField2 = aClass2.getField("RND");
        System.out.println(rndField2.get(null));

    }
}