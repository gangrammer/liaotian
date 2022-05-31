package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;


public class ServerUI extends JFrame {
    public static void main(String[] args) {
        new ServerUI();//服务器端页面
    }

    public JButton btStart;//启动服务器
    public JButton btSend;//发送信息按钮
    public JTextField tfSend;//需要发送的文本信息
    public JTextArea taShow;//信息展示
    public Server server;//用来监听客户端连接
    static List clients;//保存连接到服务器的客户端

    //构造方法
    public ServerUI() {
        super("服务器端");//设置标题
        btStart = new JButton("启动服务");//启动服务器按钮
        btSend = new JButton("发送信息");//发送信息按钮
        tfSend = new JTextField(30); //装在输入文字
        taShow = new JTextArea();//信息展示

        //点击按钮，所做的是事情，启动服务器
        btStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server = new Server(ServerUI.this);//创建服务器对象
            }
        });

        //点击发送消息按钮
        btSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server.sendMsg(tfSend.getText());//发送信息
                tfSend.setText("");//清空输入框
            }
        });

        //初始化界
        this.addWindowListener(new WindowAdapter() {

            //关闭按钮点击事件
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);//提示框
                if (a == 1) {
                    server.closeServer();//关闭服务器
                    System.exit(0); // 退出程序
                }
            }
        });

        //底部启动服务按钮与发送消息按钮
        JPanel top = new JPanel(new FlowLayout());//创建一个面板
        top.setBackground(Color.black);//设置颜色
        top.add(tfSend);//添加输入框
        top.add(btSend);//添加发送消息按钮
        top.add(btStart);//添加启动服务器按钮
        this.add(top, BorderLayout.SOUTH);//添加到面板

        //中部显示消息栏 信息展示
        final JScrollPane sp = new JScrollPane();//创建一个滚动面板
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//设置滚动条
        sp.setViewportView(this.taShow);//添加到滚动面板
        this.taShow.setEditable(false);//不可编辑
        this.taShow.setFont(new Font("宋体",Font.PLAIN,20));
        this.add(sp, BorderLayout.CENTER);//添加到面板
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭按钮
        this.setSize(700, 700);//设置窗口大小
        this.setLocation(200, 200);//设置窗口位置
        this.setVisible(true);//设置可见
    }

}