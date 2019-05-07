package com.kourou.chatroom.server.single;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SingleSever {
    public static void main(String[] args) throws IOException {
        //1.创建ServerSocket，端口号为0512
        ServerSocket serverSocket = new ServerSocket(0512);
        System.out.println("等待客户端连接...");
        //2.等待客户端连接，有客户端连接后返回客户端的Socket对象，否则线程将一直处于阻塞状态阻塞在此处
        Socket client = serverSocket.accept();
        System.out.println("有新的客户端连接，端口号为： " + client.getPort());
        //3.获取客户端的输入输出流
        Scanner clientInput = new Scanner(client.getInputStream());
        clientInput.useDelimiter("\n");
        PrintStream clientOut = new PrintStream(client.getOutputStream(),true,"UTF-8");
        //4.读取客户端输入
        if(clientInput.hasNext()){
            System.out.println(client.getInetAddress() + "说： " + clientInput.next());
        }
        //向客户端输出
        clientOut.println("hello I am Server ,welcome !");
        //关闭输入输出流
        clientInput.close();
        clientOut.close();
        serverSocket.close();
    }
}
