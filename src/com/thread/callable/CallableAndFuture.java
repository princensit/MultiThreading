package com.thread.callable;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {

    private static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executors = Executors.newCachedThreadPool();

        // If don't want to return value, then do following,
        // Future<?> future = executors.submit(new Callable<Void>() {

        Future<Integer> future = executors.submit(new Callable<Integer>() {

            public Integer call() throws IOException {
                System.out.println("Started.");

                int duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IOException("Sleep is too long");
                }

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished.");
                System.out.println("Total duration: " + duration);

                return duration;
            }
        });

        executors.shutdown();

        try {
            System.out.println("Returned value from thread: " + future.get());
        } catch (ExecutionException e) {
            IOException ex = (IOException)e.getCause();
            System.out.println(ex.getMessage());
            e.printStackTrace();
        }

    }
}
