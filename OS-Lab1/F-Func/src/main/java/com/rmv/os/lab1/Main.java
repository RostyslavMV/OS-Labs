package com.rmv.os.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rmv.os.lab1.client.FuncFClient;
import com.rmv.os.lab1.model.FunctionResult;
import spos.lab1.demo.DoubleOps;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private static int argument;

    public static void main(String[] args) {
        argument = Integer.parseInt(args[0]);
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) {
        FuncFClient client1 = new FuncFClient();
        Double valueF;
        try {
            valueF = DoubleOps.funcF(argument);
        }
        catch (InterruptedException e){
            e.printStackTrace();
            return;
        }
        client1.startConnection("127.0.0.1", 5555);
        client1.sendFunctionResult(new FunctionResult("F",valueF));
        client1.stopConnection();
    }
}
