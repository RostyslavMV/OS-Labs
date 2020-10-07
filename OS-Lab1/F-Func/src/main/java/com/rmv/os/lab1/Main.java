package com.rmv.os.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rmv.os.lab1.client.FuncFClient;
import com.rmv.os.lab1.model.FunctionResult;
import spos.lab1.demo.DoubleOps;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) {
        FuncFClient client = new FuncFClient();
        Double valueF;
        client.startConnection("127.0.0.1", 5555);
        try {
            valueF = DoubleOps.funcF(client.getArgument());
        }
        catch (InterruptedException e){
            e.printStackTrace();
            return;
        }
        client.sendFunctionResult(new FunctionResult("F",valueF));
        client.stopConnection();
    }
}
