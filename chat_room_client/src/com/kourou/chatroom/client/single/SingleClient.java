package com.kourou.chatroom.client.single;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SingleClient {
    public static void main(String[] args) {
        String serverName = "127.0.0.1";
        Integer port = 0512;
        //1.创建客户端Socket连接服务器
        try {
            Socket client = new Socket(serverName,port);
            System.out.println("连接上服务器，服务器地址为 ； " + client.getInetAddress());
            //2.获取输入输出流
            PrintStream out = new PrintStream(client.getOutputStream(),true,"UTF-8");
            Scanner in = new Scanner(client.getInputStream());
            in.useDelimiter("\n");
            //3.向服务器输出内容
            out.println("hi I am Client!");
            //4.读取服务器内容
            if(in.hasNext()){
                System.out.println("服务器发送消息为 ： " + in.next());

            }
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            System.err.println("客户端通信出现异常，错误为 ： " + e);
        }
    }
}
