package com.kourou.chatroom.client.multi;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class WriteDataToServerThread extends  Thread {
    private final Socket client;

    public WriteDataToServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);//控制台
            OutputStream outputStream = this.client.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            while(true){
                System.out.print("请输入：");
                String message = scanner.nextLine();
                printStream.println(message);
                if(message.equals("quit")){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                this.client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
