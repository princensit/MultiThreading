package com.thread.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Worker implements Runnable {

    private final CountDownLatch latch;

    Worker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed");
        latch.countDown();
    }
}

public class CountdownLatches {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        // Create thread pool of fixed size i.e. at max we have nThreads active
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 3; i++) {
            executor.submit(new Worker(latch));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
