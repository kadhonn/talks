package com.dynatrace;

import java.util.Random;

public class A implements Runnable {

    public final static int RND = new Random().nextInt();

    public void run() {
        System.out.println("A is running");
    }
}