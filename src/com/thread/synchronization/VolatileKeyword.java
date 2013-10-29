package com.thread.synchronization;

import java.util.Scanner;

class Processor extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Random number: " + Math.random());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class VolatileKeyword {

    public static void main(String[] args) {
        Processor p1 = new Processor();
        p1.start();

        // Hit return to shutdown the thread
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        p1.shutdown();
    }
}
