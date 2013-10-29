package com.thread.basic;

class Runner extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello " + i);
        }
    }
}

public class Threads1 {

    public static void main(String[] args) {
        Runner t1 = new Runner();
        Runner t2 = new Runner();
        Runner t3 = new Runner();

        t1.start();
        t2.start();
        t3.start();
    }
}
