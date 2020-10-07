package com.rmv.os.lab1.model;

import com.rmv.os.lab1.service.PauseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Results {

    public static List<FunctionResult> resultsList = Collections.synchronizedList(new ArrayList<>());

    public static void printResults() {
        for (FunctionResult result : resultsList) {
            System.out.println(result);
        }
    }

    public static void printMin() {
        boolean printed = false;
        while (!printed) {
            if(!PauseService.isPromptOpen()) {
                FunctionResult minResult = resultsList.get(0);
                for (int i = 1; i < resultsList.size(); i++) {
                    if (resultsList.get(i).getResult() < minResult.getResult()) {
                        minResult = resultsList.get(i);
                    }
                }
                System.out.println("Minimal result = " + minResult);
                printed = true;
            }
        }
        PauseService.end();
    }
}
