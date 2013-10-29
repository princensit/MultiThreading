package com.thread.synchronization;

public class SynchronizedKeyword {

    private int count = 0;

    public static void main(String[] args) {
        SynchronizedKeyword s = new SynchronizedKeyword();
        s.doWork();
    }

    public synchronized void increment() {
        count++; // count = count + 1 (3 steps)
    }

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count is: " + count);
    }
}
