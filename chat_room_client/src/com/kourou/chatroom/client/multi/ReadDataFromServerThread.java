package com.kourou.chatroom.client.multi;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadDataFromServerThread extends Thread {
    private final Socket client;

    public ReadDataFromServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        //TODO
        try {
            InputStream in = this.client.getInputStream();
            Scanner scanner = new Scanner(in);
            while(true){
                try {
                    String message = scanner.nextLine();
                    System.out.println(message);
                }catch (NoSuchElementException e){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
