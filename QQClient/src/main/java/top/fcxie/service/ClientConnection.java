package top.fcxie.service;

import top.fcxie.common.Message;
import top.fcxie.common.MessageType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端线程
 */
public class ClientConnection implements Runnable{
    //关联连接的socket
    private Socket socket;

    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //死循环维系与服务端的连接
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if(message.getType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    String[] friends =  message.getContent().split(",");
                    System.out.println("=========当前在线好友=========");
                    for(String str : friends){
                        System.out.println("好友："+str);
                    }
                }else if(message.getType().equals(MessageType.MESSAGE_COMM_MES)){
                    System.out.println(message.getSender()+"对你说:"+message.getContent());
                }else if(message.getType().equals(MessageType.MESSAGE_CHAT_FAIL)){
                    System.out.println("用户："+message.getReceiver()+"不存在!");
                }else if(message.getType().equals(MessageType.MESSAGE_ALL_MES)){
                    System.out.println(message.getSender()+"对大家说:"+message.getContent());
                }else if(message.getType().equals(MessageType.MESSAGE_SYS_MES)){
                    System.out.println("系统消息："+message.getContent());
                }else if(message.getType().equals(MessageType.MESSAGE_SEND_FILE)){
                    System.out.println("接收用户："+message.getSender()+"发送的文件");
                    //打开文件
                    File file = new File(message.getDest());
                    //打开文件输出流
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(message.getFile());
                    fos.close();
                    System.out.println("文件已存至:"+message.getDest());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
