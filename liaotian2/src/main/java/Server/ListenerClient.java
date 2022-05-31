package Server;

import mapper.usermapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.user;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class ListenerClient extends Thread {
    BufferedReader reader;//输出流
    PrintWriter writer;//输入流
    ServerUI ui;//窗口
    Socket client;//客户端

    //构造函数
    public ListenerClient(ServerUI ui, Socket client) {
        this.ui = ui;
        this.client = client;
        this.start();
    }

    //为每一个客户端创建线程等待接收信息，然后把信息广播出去
    @Override
    public void run() {
        String msg = "";//接收信息
        while (true) {
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));//获取输入流
                writer = new PrintWriter(client.getOutputStream(), true);//获取输出流
                msg = reader.readLine();//读取信息
//               获取IP msg 创建user 保存到数据库
                InetAddress inetAddress = client.getInetAddress();
                user user = new user(String.valueOf(inetAddress), msg);
                insert(user);

                sendMsg(msg);//发送信息
            } catch (IOException e) {
                println("已经下线用户的详细信息"+client);
                break;
            }
            if (msg != null && msg.trim() != "") {
                println("客户端 " + msg);//显示信息
            }
        }
    }
    //把信息广播到所有用户
    public synchronized void sendMsg(String msg) {
        try {
            //遍历所有客户端
            for (int i = 0; i < ui.clients.size(); i++) {
                Socket client = (Socket) ui.clients.get(i);//获取客户端
                writer = new PrintWriter(client.getOutputStream(), true);//获取输出流
                writer.println(msg);//发送信息
            }
        } catch (Exception e) {
            println(e.toString());
        }
    }

    //显示信息方法
    public void println(String s) {
        if (s != null) {
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");
            System.out.println(s + "\n");
        }
    }

    //保存数据到数据库
    public void insert(user user) throws IOException {
        //2.1 获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        usermapper mapper = sqlSession.getMapper(usermapper.class);
        mapper.insert(user.getIp(),user.getMsg());
        sqlSession.commit();
        sqlSession.close();

    }
}