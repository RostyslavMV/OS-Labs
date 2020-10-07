package com.rmv.os.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rmv.os.lab1.client.FuncGClient;
import com.rmv.os.lab1.model.FunctionResult;
import spos.lab1.demo.DoubleOps;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) {
        FuncGClient client = new FuncGClient();
        Double valueG;
        client.startConnection("127.0.0.1", 5555);
        try {
            valueG = DoubleOps.funcG(client.getArgument());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        client.sendFunctionResult(new FunctionResult("G", valueG));
        client.stopConnection();
    }
}
