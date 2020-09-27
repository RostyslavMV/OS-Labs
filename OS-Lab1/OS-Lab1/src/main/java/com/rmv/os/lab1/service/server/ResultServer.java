package com.rmv.os.lab1.service.server;

import lombok.Getter;
import org.springframework.stereotype.Service;
import com.rmv.os.lab1.model.FunctionResult;
import com.rmv.os.lab1.model.Results;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
@Getter
public class ResultServer {

    private ServerSocket serverSocket;

    private Thread serverThread;

    public void start(int port) {
        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    new ClientHandler(serverSocket.accept()).start();
                }
            } catch (IOException e) {
                stop();
            }
        });
        serverThread.start();
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                FunctionResult inputResult;
                Object inputObject;
                while (true) {
                    try {
                        if ((inputObject = in.readObject()) != null) {
                            inputResult = (FunctionResult) inputObject;
                            System.out.println(inputResult);
                            Results.resultsList.add(inputResult);
                            if (Results.resultsList.size() == 2) {
                                Results.printMin();
                                System.exit(0);
                                break;
                            }
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}