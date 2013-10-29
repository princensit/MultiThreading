package com.thread.app;

import java.util.Scanner;

public class WaitNotify {

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer thread running...");
            Thread.sleep(2000);
            System.out.println("Produced. Waiting to consume...");
            wait();
            System.out.println("Producer resumed");

        }
    }

    public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        synchronized (this) {
            System.out.println("Consumer consuming...");
            Thread.sleep(2000);
            System.out.println("Consumed. Press return key.");
            scanner.nextLine();
            notify();
            Thread.sleep(1000);
            System.out.println("Relinquished the lock");
        }
    }

    public static void main(String[] args) {
        final WaitNotify waitNotify = new WaitNotify();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    waitNotify.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    waitNotify.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
    }
}
