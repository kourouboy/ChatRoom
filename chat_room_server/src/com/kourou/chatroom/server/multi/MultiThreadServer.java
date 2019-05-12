package com.kourou.chatroom.server.multi;

import sun.dc.pr.PRError;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadServer {

    //支持100client
    //1.thread -> 10 client

    private static  final ExecutorService executor = Executors.newFixedThreadPool(10, new ThreadFactory() {
        private final AtomicInteger id = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Thread-Client-Handler" + id.getAndIncrement());
            return t;
        }
    });
    public static void main(String[] args) {
        //1.键盘输入（几乎用不到）
        //2.参数String[] args
        //3.文件 Properties
        //4.数据库

        try {
            ServerSocket serverSocket = new ServerSocket(5120);
            System.out.println("服务端启动：" + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());

            while (true){
                final Socket client = serverSocket.accept();

                //使用线程池
                executor.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
