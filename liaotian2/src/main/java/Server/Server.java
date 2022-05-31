package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread {
    ServerUI ui;//服务器页面
    ServerSocket ss;//服务器端的ServerSocket
    BufferedReader reader;//输出流
    PrintWriter writer;//输入流

    //初始化方法
    public Server(ServerUI ui) {
        this.ui = ui;
        this.start();//创建线程
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(8888);//创建ServerSocket
            ui.clients = new ArrayList<>();//创建客户端列表
            println("启动服务器成功：端口8888");//打印服务器启动成功
            while (true) {
                println("等待客户端链接");
                Socket client = ss.accept();//接收客户端的连接

//                获取IP保存到数据库
                InetAddress inetAddress = client.getInetAddress();


                ui.clients.add(client);//将客户端添加到客户端列表
                println("连接成功，客户端请求服务端的详细信息：" + client.toString());//打印客户端请求服务端的详细信息
                new ListenerClient(ui, client);//创建监听客户端线程
            }
        } catch (IOException e) {
            println("启动服务器失败：8888");//打印服务器启动失败
            println("e.toString()");
            e.printStackTrace();
        }

    }

    //发送到客户端方法
    public synchronized void sendMsg(String msg) {
        try {
            for (int i = 0; i < ui.clients.size(); i++) {
                Socket client = (Socket) ui.clients.get(i);//获取客户端
                writer = new PrintWriter(client.getOutputStream(), true);//获取输出流
                writer.println("系统提示:"+msg);//发送消息
            }
        } catch (Exception e) {
            println(e.toString());
        }
    }

    //打印方法
    public void println(String s) {
        if (s != null) {
            s = "服务端打印消息：" + s;
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");//打印消息
            System.out.println(s + "\n");
        }
    }

    //关闭方法
    public void closeServer() {
        try {
            if (ss != null)
                ss.close();//关闭服务器
            if (reader != null)
                reader.close();//关闭输入流
            if (writer != null)
                writer.close();//关闭输出流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}