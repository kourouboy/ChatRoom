package com.kourou.chatroom.client.single;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SingleClient {
    public static void main(String[] args) {

        //客户端

        String host = "127.0.0.1";
        int port = 5120;
        try {
            //1.创建客户端Socker和服务区建立连接
            final Socket socket = new Socket(host,port);

            //2.客户端进行接收和发送数据（先发送后接收）
            //2.1 发送数据（write）
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println("服务器我来了。。。");
            printStream.flush();

            //2.2 接收数据（read）
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String message = scanner.next();
            System.out.println("从服务端接收的数据： " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
