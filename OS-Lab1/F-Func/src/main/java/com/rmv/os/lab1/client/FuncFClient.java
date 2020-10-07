package com.rmv.os.lab1.client;

import com.rmv.os.lab1.model.FunctionResult;
import lombok.Getter;

import java.io.*;
import java.net.Socket;

public class FuncFClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    @Getter
    private Integer argument;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            boolean argumentNotPassed= true;
            Object inputObject;
            while (argumentNotPassed){
                if ((inputObject = in.readObject()) != null) {
                    argument = (Integer) inputObject;
                    argumentNotPassed = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendFunctionResult(FunctionResult functionResult) {
        try {
            out.writeObject(functionResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
