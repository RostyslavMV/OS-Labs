package com.rmv.os.lab1;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rmv.os.lab1.model.Results;
import com.rmv.os.lab1.service.KeyService;
import com.rmv.os.lab1.service.PauseService;
import com.rmv.os.lab1.service.server.ResultServer;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    @Getter
    @Autowired
    private static ResultServer resultServer;

    @Getter
    private static Process processF;
    @Getter
    private static Process processG;

    public static void main(String[] args) {
        run();
    }

    public static void run(String... args) {
        resultServer = new ResultServer();
        resultServer.start(5555);

        String thisProjectDir = System.getProperty("user.dir");
        thisProjectDir = thisProjectDir.substring(0, thisProjectDir.length() - 7);
        String pathF = thisProjectDir + "F-Func\\target\\F-Func-1.0-SNAPSHOT.jar";
        String pathG = thisProjectDir + "G-Func\\target\\G-Func-1.0-SNAPSHOT.jar";

        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        ResultServer.setArgument(x);

        ProcessBuilder processBuilderF = new ProcessBuilder("java", "-jar", pathF);
        ProcessBuilder processBuilderG = new ProcessBuilder("java", "-jar", pathG);

        new Thread(() -> {
            try {
                Process funcF = processBuilderF.start();
                processF = funcF;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            Process funcG = processBuilderG.start();
            processG = funcG;
        } catch (IOException e) {
            e.printStackTrace();
        }
        KeyService keyService = new KeyService();
        keyService.start();

    }
}
