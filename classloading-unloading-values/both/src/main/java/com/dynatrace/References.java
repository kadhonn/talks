package com.dynatrace;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public class References {
    public static void main(String[] args) throws Exception {
        long count = 0;
        Set<Long> objectSet = new HashSet<>();

        A a = new A();

        Reference<?> objectWeakReference = new SoftReference<>(a);

        a = null;

        while (objectWeakReference.get() != null) {
            System.gc();
            System.out.print(".");
            for (int i = 0; i < 100000; i++) {
                objectSet.add(count++);
            }
            Thread.sleep(200);
        }
        System.out.println();
        System.out.println("wohooo");
        System.out.println(objectSet.size());
        System.out.println(objectWeakReference.get());
    }

}
