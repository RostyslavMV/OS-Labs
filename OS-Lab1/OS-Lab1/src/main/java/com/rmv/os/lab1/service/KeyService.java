package com.rmv.os.lab1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Service
public class KeyService extends JComponent {
    JFrame frame;

    public void start() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(0, 0);
        frame.setVisible(true);

        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        frame.addKeyListener(new MyKey());
        frame.setFocusable(true);
    }


    private class MyKey extends KeyAdapter implements ActionListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                PauseService.end();
            } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                PauseService.startConsolePrompt();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
