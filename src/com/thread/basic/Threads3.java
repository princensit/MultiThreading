package com.thread.basic;

public class Threads3 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Hi " + i);
                }
            }
        });

        t1.start();
    }
}
