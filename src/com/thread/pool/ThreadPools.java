package com.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Worker implements Runnable {

    private final int id;

    Worker(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Starting: " + id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed: " + id);
    }
}

public class ThreadPools {

    public static void main(String[] args) {

        // Create thread pool of fixed size i.e. at max we have nThreads active
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            executor.submit(new Worker(i));
        }

        executor.shutdown();
        System.out.println("All tasks submitted");

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed");
    }
}
