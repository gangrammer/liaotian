package pojo;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("tb_msg")
public class user {
    private int id;
    private String ip;
    private String msg;

    public user(String ip, String msg) {
        this.ip = ip;
        this.msg = msg;
    }

    public user(int id, String ip, String msg) {
        this.id = id;
        this.ip = ip;
        this.msg = msg;
    }

    public user() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
