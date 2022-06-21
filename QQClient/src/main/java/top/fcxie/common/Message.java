package top.fcxie.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 服务端与客户端通信消息类
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;//提高兼容性

    private String sender;//发送者
    private String receiver;//接收者
    private String content;//内容
    private String type;//类型
    private String sendTime;//发送时间

    private byte[] file;//文件的字节形式
    private int fileSize;//文件长度
    private String dest;//目的地址
    private String src;//源地址

    public String getSendTime() {
        return sendTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", file=" + Arrays.toString(file) +
                ", fileSize=" + fileSize +
                ", dest='" + dest + '\'' +
                ", src='" + src + '\'' +
                '}';
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }


    public Message() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
