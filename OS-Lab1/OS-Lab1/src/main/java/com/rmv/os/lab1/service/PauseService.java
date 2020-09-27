package com.rmv.os.lab1.service;

import com.rmv.os.lab1.Main;

import java.util.Scanner;

public class PauseService {

    private static final int MAX_TIME_MS = 15000;

    public static void end() {
        Main.getProcessF().destroy();
        Main.getProcessG().destroy();
        Main.getResultServer().stop();
        System.exit(0);
    }

    public static void startConsolePrompt() {
        System.out.println("Cancellation Prompt:");
        System.out.println("(a) stop");
        System.out.println("(b) continue");
        Scanner scanner = new Scanner(System.in);
        long promptStartTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - promptStartTime < MAX_TIME_MS) {
            String userInput = scanner.nextLine();
            if (userInput.equals("a") || userInput.equals("stop")) {
                end();
            } else if (userInput.equals("b") || userInput.equals("continue")) {
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
        end();
    }
}
