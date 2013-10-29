package com.thread.app;

import java.util.LinkedList;
import java.util.Random;

public class ProducerConsumerWaitNotify {

    private LinkedList<Integer> list = new LinkedList<Integer>();

    private final Object lock = new Object();

    private static final int LIMIT = 10;

    public void produce() throws InterruptedException {
        int count = 0;
        while (true) {
            synchronized (lock) {
                while (list.size() == LIMIT) {
                    lock.wait();
                }

                list.add(count++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }

                System.out.print("List size: " + list.size());
                int value = list.removeFirst();
                System.out.println("; Value: " + value);
                lock.notify();

                Thread.sleep(random.nextInt(1000));
            }
        }
    }

    public static void main(String[] args) {
        final ProducerConsumerWaitNotify waitNotify = new ProducerConsumerWaitNotify();

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
