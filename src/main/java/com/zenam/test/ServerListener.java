package com.zenam.test;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ServerListener {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    @PostConstruct
    public void start() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(13007);
                System.out.println("connected on 13007");
                try {
                    clientSocket = serverSocket.accept();
                    InputStream stream = clientSocket.getInputStream();
                    System.out.println("connection: " + clientSocket.getInetAddress().getHostAddress() +" size: " + stream.available());
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = stream.read(buffer)) != -1) {
                        System.out.println("in");
                        String data = new String(buffer, 0, bytesRead);
                        System.out.println(data);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }).start();
    }


    public void startClient(){
        new Thread(() -> {
            try{
                clientSocket = new Socket("10.6.103.115", 13007);
                System.out.println("connected to: "+clientSocket.getInetAddress().getHostAddress()+clientSocket.getPort());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
        }
        }).start();
    }
}
