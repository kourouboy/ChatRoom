package com.kourou.chatroom.server.multi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//业务逻辑
public class ClientHandler implements  Runnable{

    private static final Map<String,Socket> SOCKET_MAP = new ConcurrentHashMap<>();


    //Socket -> in out
    private final Socket client;
    //当前客户端注册名称
    private String currentName;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        //TODO 业务实现
        try {
            InputStream in = this.client.getInputStream();
            Scanner scanner = new Scanner(in);
            while (true){
                String line = scanner.nextLine();
                if(line.startsWith("register")){
                    //注册流程
                    String[] segments = line.split(":");
                    if(segments.length == 2 && segments[0].equals("register")){
                        String name = segments[1];
                        register(name);
                    }
                    continue;
                }
                if (line.startsWith("group")){
                    //群聊流程
                    String[] segments = line.split(":");
                    if(segments.length == 2 && segments[0].equals("group")){
                        String message = segments[1];
                        GroupChat(message);
                    }
                    continue;
                }
                if(line.startsWith("private")){
                    //私聊流程
                    String[] segments = line.split(":");
                    if(segments.length == 3 && segments[0].equals("private")){
                        String name = segments[1];
                        String message = segments[2];
                        PrivateChat(name,message);
                    }

                    continue;
                }
                if (line.startsWith("quit")){
                    //私聊流程
                    quit();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void quit() {
        //退出方法
        //迭代器
        Iterator<Map.Entry<String,Socket>> iterator = SOCKET_MAP.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String ,Socket> entry = iterator.next();

            if(entry.getValue() == this.client){
                System.out.println(entry.getKey() + "退出");
                iterator.remove();
                break;
            }
        }
        printOlineClient();
    }

    private void PrivateChat(String name, String message) {
        //私聊方法
//        Set<Map.Entry<String,Socket>> entrySet = SOCKET_MAP.entrySet();
//        String currentName = " ";
//        for (Map.Entry<String ,Socket> entry : entrySet) {
//            String key = entry.getKey();
//            Socket socket = entry.getValue();
//            if (socket == this.client) {
//                currentName = key;//name
//            }
//        }
        Socket socket = SOCKET_MAP.get(name);
        if(socket != null){
            this.sendMessage(socket,this.currentName + "说：" + message);
        }
    }

    private void GroupChat(String message) {
        //群聊方法
        //发送消息给当前在线的客户端，不包含自己
        //谁   发   发消息


        for(Map.Entry<String,Socket> entry : SOCKET_MAP.entrySet()){
            Socket socket = entry.getValue();
            if(socket != this.client){
                this.sendMessage(socket,this.currentName + "说：" + message);
            }
        }

    }

    private void register(String name) {
        //注册方法

        //name -> Socket
        //key -> value
        SOCKET_MAP.put(name,this.client);
        this.currentName = name;
        this.sendMessage(this.client,"恭喜 <" + name + "> 注册成功");
        printOlineClient();

    }
    public void printOlineClient(){
        System.out.println("当前在线的客户端有" + SOCKET_MAP.size() + "名称列表如下：");
        for(String key : SOCKET_MAP.keySet()){
            System.out.println(key);
        }
    }

    public void sendMessage(Socket socket ,String message){
        try {
            OutputStream out = socket.getOutputStream();
            PrintStream printStream = new PrintStream(out);
            printStream.println(message);
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //扩展点：如何处理异常关闭（比如：直接关闭客户端进程）
    //Socket  ->  写数据  如果抛异常  关闭此客户端
}
