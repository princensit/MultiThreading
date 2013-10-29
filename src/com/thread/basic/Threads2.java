package com.thread.basic;

class RunnableImpl implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hey " + i);
        }
    }
}

public class Threads2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new RunnableImpl());
        Thread t2 = new Thread(new RunnableImpl());
        Thread t3 = new Thread(new RunnableImpl());

        t1.start();
        t2.start();
        t3.start();
    }
}
