package com.rmv.os.lab1.service;

import com.rmv.os.lab1.Main;
import lombok.Getter;

import java.util.Scanner;

public class PauseService {

    private static final int MAX_TIME_MS = 15000;

    @Getter
    private static volatile boolean promptOpen;

    public static void end() {
        Main.getResultServer().stop();
        Main.getProcessF().destroy();
        Main.getProcessG().destroy();
        System.exit(0);
    }

    public static void startConsolePrompt() {
        promptOpen = true;
        System.out.println("Cancellation Prompt:");
        System.out.println("(a) stop");
        System.out.println("(b) continue");
        Scanner scanner = new Scanner(System.in);
        long promptStartTime = System.currentTimeMillis();
        new Thread(() -> {
            while (System.currentTimeMillis() - promptStartTime < MAX_TIME_MS) {
                String userInput = scanner.nextLine();
                if (userInput.equals("a") || userInput.equals("stop")) {
                    end();
                } else if (userInput.equals("b") || userInput.equals("continue")) {
                    promptOpen = false;
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }).start();
        try {
            Thread.sleep(MAX_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end();
    }
}
