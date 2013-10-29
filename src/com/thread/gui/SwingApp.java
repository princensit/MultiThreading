package com.thread.gui;

import javax.swing.*;

public class SwingApp {

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame("SwingWorker Demo");
            }
        });
    }
}
