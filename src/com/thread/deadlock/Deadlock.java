package com.thread.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

    private Random random = new Random();

    private Account acc1 = new Account();

    private Account acc2 = new Account();

    private Lock lock1 = new ReentrantLock();

    private Lock lock2 = new ReentrantLock();

    // Acquire locks with default timeout
    public void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
            // Acquire locks
            boolean firstLockAcquired = false;
            boolean secondLockAcquired = false;
            try {
                firstLockAcquired = firstLock.tryLock();
                secondLockAcquired = secondLock.tryLock();
            } finally {
                if (firstLockAcquired && secondLockAcquired) {
                    return;
                }
                if (firstLockAcquired) {
                    firstLock.unlock();
                }
                if (secondLockAcquired) {
                    secondLock.unlock();
                }
            }

            // Locks not acquired
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            acquireLocks(lock1, lock2);

            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            acquireLocks(lock2, lock1);

            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + acc1.getBalance());
        System.out.println("Account 2 balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
    }

    public static void main(String[] args) {
        final Deadlock d = new Deadlock();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    d.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    d.secondThread();
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

        d.finished();
    }
}
