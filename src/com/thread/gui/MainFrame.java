package com.thread.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

public class MainFrame extends JFrame {

    private JLabel countLabel = new JLabel("0");

    private JLabel statusLabel = new JLabel("Task not completed");

    private JButton startButton = new JButton("Start");

    public MainFrame(String title) throws HeadlessException {
        super(title);
        setLayout(new GridLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.NONE;

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 1;
        add(countLabel, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        add(statusLabel, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.weightx = 1;
        gc.weighty = 1;
        add(startButton, gc);

        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void start() {
        // Use SwingWorker<Void, Void> and return null from doInBackground if
        // you don't want any final result and you don't want to update the GUI
        // as the thread goes along.
        // First argument is the thread result, returned when processing finished.
        // Second argument is the value to update the GUI with via publish() and process()
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {

            @Override
            /*
             * Note: do not update the GUI from within doInBackground.
             */
            protected Boolean doInBackground() throws Exception {

                // Simulate useful work
                for (int i = 0; i < 30; i++) {
                    Thread.sleep(100);
                    System.out.println("Hello: " + i);

                    // optional: use publish to send values to process(), which
                    // you can then use to update the GUI.
                    publish(i);
                }

                return false;
            }

            @Override
            // This will be called if you call publish() from doInBackground()
            // Can safely update the GUI here.
            protected void process(List<Integer> chunks) {
                Integer value = chunks.get(chunks.size() - 1);

                countLabel.setText("Current value: " + value);
            }

            @Override
            // This is called when the thread finishes.
            // Can safely update GUI here.
            protected void done() {

                try {
                    Boolean status = get();
                    statusLabel.setText("Completed with status: " + status);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

        };

        worker.execute();
    }
}
