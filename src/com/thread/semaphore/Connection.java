package com.thread.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Singleton class
 */
public class Connection {

    private static Connection instance = new Connection();

    private int connections = 0;

    // If fair is set to true, then this semaphore will guarantee first-in first-out granting of permits under
    // contention
    private Semaphore sem = new Semaphore(10, true);

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } finally {
            sem.release();
        }

        System.out.println("Semaphore available permits: " + sem.availablePermits());
    }

    public void doConnect() {
        synchronized (this) {
            connections++;
            System.out.println("Current connections: " + connections);
        }

        try {
            // do some task
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }
    }
}
