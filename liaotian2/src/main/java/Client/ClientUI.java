package Client;

import pojo.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;


public class ClientUI extends JFrame {
    public static void main(String[] args) {
        new ClientUI();//启动客户端
    }

    public JButton btStart;//开始连接按钮
    public JButton btSend;//发送消息按钮
    //    gan
    public JButton btSelect;
    public JTextField tfSend; //装在输入文字
    public JTextPane nameText; //输入名字
    public JTextPane ipTex; //输入名字
    public JTextPane portText; //输入名字
    public JTextField tfName; //服务器ip
    public JTextField tfIP; //服务器ip
    public JTextField tfPort; //服务器端口
    public JTextArea taShow;//显示消息
    public Client server;//客户端类

    public ClientUI() {
        super("客户端");//设置窗口标题
        btStart = new JButton("启动连接");//创建开始连接按钮
        btSend = new JButton("发送信息");//创建发送消息按钮
        btSelect = new JButton("查询聊天记录");
        tfSend = new JTextField(30);//创建输入文字
        tfIP = new JTextField(10);//创建输入ip输入框
        tfPort = new JTextField(5);//创建输入端口输入框
        tfName = new JTextField(10);//创建输入名字输入框
        nameText = new JTextPane();//创建名字提示输入框
        nameText.setText("登录名");//设置名字提示输入框的文字
        nameText.setEditable(false);//设置名字提示输入框不可编辑
        ipTex = new JTextPane();//创建ip提示输入框
        ipTex.setText("服务地址");//设置ip提示输入框的文字
        ipTex.setEditable(false);//设置ip提示输入框不可编辑
        portText = new JTextPane();//创建端口提示输入框
        portText.setText("服务端口");//设置端口提示输入框的文字
        portText.setEditable(false);//设置端口提示输入框不可编辑
        taShow = new JTextArea();//创建显示消息的文本域
        taShow.setFont(new Font("宋体",Font.PLAIN,20));


        //启动链接按钮事件
        btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server = new Client(ClientUI.this);//创建客户端类
            }
        });

        //发送按钮事件
        btSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();//获取名字
                if (name == null || "".equals(name)) {
                    name = "匿名者";//如果名字为空，则设置为匿名者
                }
                server.sendMsg(name + "：" + tfSend.getText());//发送消息
                tfSend.setText("");//清空输入框
            }
        });
//        gan 点击查询聊天记录按键事件
        btSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<user> users = server.selectAll();
                    for (user user : users
                    ) {
                        String msg = user.getMsg();
                        String substring = msg.substring(0, 2);
                        if (substring.equals("会员")) {
                            taShow.append("\n");
                        } else {
                            taShow.append(msg + "\n");
                        }


                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //调用mapper
                //信息返回到文本框
            }
        });

        //设置窗口关闭事件
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);//提示是否关闭
                if (a == 1) {
                    System.exit(0); //关闭
                }
            }

        });

        //底部的发送信息框与链接按钮
        JPanel top = new JPanel(new FlowLayout());//创建面板
        top.setBackground(Color.pink);//设置颜色
        top.add(tfSend); //面板添加发送文本
        top.add(btSend); //面板添加发送按钮
        top.add(btSelect);
        this.add(top, BorderLayout.SOUTH); //加载到底部

        //头部放连接服务的
        JPanel northJpannel = new JPanel(new FlowLayout());//创建面板
        northJpannel.setBackground(Color.pink);//设置颜色
        northJpannel.add(nameText);//面板添加名字提示输入框的文字
        northJpannel.add(tfName);
        northJpannel.add(ipTex);
        northJpannel.add(tfIP);
        northJpannel.add(portText);
        northJpannel.add(tfPort);
        northJpannel.add(btStart);
        this.add(northJpannel, BorderLayout.NORTH); //加载到头部
        final JScrollPane sp = new JScrollPane();//添加到滚动面板
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setViewportView(this.taShow);
        this.taShow.setEditable(false);
        this.add(sp, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
        this.setSize(700, 700);//设置大小
        this.setLocation(600, 200);//设置位置
        this.setVisible(true);//设置可见
    }
}