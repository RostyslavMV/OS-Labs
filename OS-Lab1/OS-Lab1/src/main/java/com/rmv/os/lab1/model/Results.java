package com.rmv.os.lab1.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Results {

    @Getter
    private static final long MAX_TIME_MS = 20000;

    public static List<FunctionResult> resultsList = new ArrayList<>();

    public static void printResults() {
        for (FunctionResult result : resultsList) {
            System.out.println(result);
        }
    }

    public static void printMin() {
        FunctionResult minResult = resultsList.get(0);
        for (int i = 1; i < resultsList.size(); i++) {
            if (resultsList.get(i).getResult() < minResult.getResult()) {
                minResult = resultsList.get(i);
            }
        }
        System.out.println("Minimal result = " + minResult);
    }

    public static void printFunctionHangs() {
        try {
            Thread.sleep(MAX_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean finishedF = false;
        boolean finishedG = false;
        for (FunctionResult result : resultsList) {
            if (result.getFunctionName().equals("F")) {
                finishedF = true;
            } else if (result.getFunctionName().equals("G")) {
                finishedG = true;
            }
        }
        if (!finishedF) {
            System.out.println("F hangs");
        }
        if (!finishedG) {
            System.out.println("G hangs");
        }
        printMin();
    }
}
