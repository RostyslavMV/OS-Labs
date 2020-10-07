package com.rmv.os.lab1.service.server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import com.rmv.os.lab1.model.FunctionResult;
import com.rmv.os.lab1.model.Results;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class ResultServer {

    private ServerSocket serverSocket;

    private Thread serverThread;

    private List<ClientHandler> clientHandlers;

    @Setter
    private static int argument;

    public void start(int port) {
        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                clientHandlers = new ArrayList<>();
                while (true) {
                    ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
                    clientHandlers.add(clientHandler);
                    clientHandler.start();
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
                out.writeObject(Integer.valueOf(ResultServer.argument));
                FunctionResult inputResult;
                Object inputObject;
                while (true) {
                    try {
                        if ((inputObject = in.readObject()) != null) {
                            inputResult = (FunctionResult) inputObject;
                            Results.resultsList.add(inputResult);
                            if (Results.resultsList.size() == 2 || inputResult.getResult().equals(0.0)) {
                                Results.printMin();
                                break;
                            }
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
                end();
            } catch (IOException e) {
                end();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public void end() {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}