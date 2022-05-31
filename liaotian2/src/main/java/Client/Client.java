package Client;

import mapper.usermapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.user;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client extends Thread {
    ClientUI ui;//客户端的界面
    Socket client;//客户端的socket
    BufferedReader reader;//客户端的输入流
    PrintWriter writer;//客户端的输出流

    //构造函数
    public Client(ClientUI ui) {
        this.ui = ui;//设置客户端的界面
        try {
            String ip = ui.tfIP.getText(); //得到输入的ip地址
            int port = Integer.parseInt(ui.tfPort.getText()); //得到输入的端口
            client = new Socket(ip,port);//这里设置连接服务器端的IP的端口
            println("连接服务器成功，服务器端口地址：" + port);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));//获取输入流
            writer = new PrintWriter(client.getOutputStream(), true);//获取输出流
            String name = ui.tfName.getText();//得到输入的用户名
            if (name == null || "".equals(name)) {
                name = "匿名者";//如果用户名为空，则设置为匿名者
            }
            sendMsg("会员 " + name + ",登录上来了");//发送登录信息
            // 如果为 true，则 println、printf 或 format 方法将刷新输出缓冲区
        } catch (NumberFormatException nu) {
            println("端口请输入正确.......");
            nu.printStackTrace();
        } catch (IOException e) {
            println("连接服务器失败：请输入正确的IP地址与端口");
            println(e.toString());
            e.printStackTrace();
        }
        this.start();//启动线程
    }

    public void run() {
        String msg = "";//定义一个字符串用于接收服务器端的信息
        while (true) {
            try {
                msg = reader.readLine();//读取服务器端的信息
            } catch (IOException e) {
                println("服务器断开连接");//如果服务器端断开连接，则打印断开连接
                break;
            }
            if (msg != null && msg.trim() != "") {
                println(msg);//打印服务器端的信息
            }
        }
    }

    //发送信息的方法
    public void sendMsg(String msg) {
        try {
            writer.println(msg);//发送信息
        } catch (Exception e) {
            println(e.toString());
        }
    }

    //打印信息的方法
    public void println(String s) {
        if (s != null) {
            this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");//打印信息
            System.out.println(s + "\n");
        }
    }
    //查询聊天记录
    public List<user> selectAll() throws IOException {
        //2.1 获取SqlSessionFactory对象
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //2.2 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        usermapper mapper = sqlSession.getMapper(usermapper.class);
        List<user> users = mapper.selectAll();
       return users;
    }
}