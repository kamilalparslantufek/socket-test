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
                while(true){
                    try {
                        clientSocket = serverSocket.accept();
                        System.out.println("connection: " + clientSocket.getInetAddress().getHostAddress());
                        DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                        String line ="";
                        while(!line.equals("F3|END_OF_RECORDS")){
                            try{
                                line = in.readUTF();
                                System.out.println(line);
                            }
                            catch (Exception e){
                                System.out.println(e);
                                break;
                            }
                        }
                        System.out.println("connection closing...");
                        clientSocket.close();
                        in.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
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
