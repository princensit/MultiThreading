package com.thread.app;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {

    private int count = 0;

    private final Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increment() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public void firstThread() throws InterruptedException {
        lock.lock();

        System.out.println("Waiting...");
        condition.await();
        System.out.println("Woken up...");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000); // This is done so that firsThread starts first
        lock.lock();

        System.out.println("Press the return key.");
        new Scanner(System.in).nextLine();
        System.out.println("Got the return key.");

        condition.signal();
        System.out.println("Sent condition signal.");

        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.println("Count: " + count);
    }

    public static void main(String[] args) {
        final ReentrantLocks r = new ReentrantLocks();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    r.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    r.secondThread();
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

        r.finished();
    }
}
