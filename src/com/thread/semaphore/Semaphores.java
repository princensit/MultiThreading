package com.thread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Semaphores {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            executors.submit(new Runnable() {

                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }

        executors.shutdown();
        executors.awaitTermination(1, TimeUnit.DAYS);
    }
}
