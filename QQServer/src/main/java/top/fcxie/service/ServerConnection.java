package top.fcxie.service;

import top.fcxie.common.Message;
import top.fcxie.common.MessageType;
import top.fcxie.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务端与客户端连接的线程
 */
public class ServerConnection implements Runnable{
    //客户端socket
    private Socket socket;
    //用户名
    private String username;

    private MessageService messageService = new MessageService();

    private UserService userService = new UserService();

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public ServerConnection(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run() {
        //死循环维系与客户端的连接
        while (true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                if(message.getType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){//用户请求在线列表
                    getOnlineUser(message);
                }else if(message.getType().equals(MessageType.MESSAGE_COMM_MES)){//用户请求私聊
                    if(!privateChat(message)) continue;
                }else if(message.getType().equals(MessageType.MESSAGE_ALL_MES)){
                    chatToAll(message);
                }else if(message.getType().equals(MessageType.MESSAGE_SEND_FILE)){
                   sendFile(message);
                }else if(message.getType().equals(MessageType.MESSAGE_CLIENT_EXIT)){//客户端退出
                    clientExit(message);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void getOnlineUser(Message message) throws IOException {
        System.out.println("用户:"+message.getSender()+"请求好友列表");
        Socket socket = this.getSocket();
        Message mes = new Message();
        mes.setType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
        mes.setContent(ServerThreadManager.getClientList());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(mes);
    }

    private void clientExit(Message message) throws IOException {
        System.out.println("用户:"+message.getSender()+"已退出");
        //获取客户端线程，终止该线程
        ServerThreadManager.removeClientThread(username);
        //关闭socket
        socket.close();
    }

    private boolean privateChat(Message message) throws IOException {
        //判断用户是否存在
        User user;
        user =  userService.getUser(message.getReceiver());
        if(user.getUserName() == null || user.getUserName().equals("")){//不存在用户
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message msg = new Message();
            msg.setType(MessageType.MESSAGE_CHAT_FAIL);
            msg.setReceiver(message.getReceiver());
            oos.writeObject(msg);
            oos.flush();
            return false;
        }
        //判断私聊对象是否在线
        ServerConnection receiver = (ServerConnection) ServerThreadManager.getClientThread(message.getReceiver());
        //在线则转发消息
        if(receiver != null){
            //转发消息至接收者
            ObjectOutputStream oos = new ObjectOutputStream(receiver.getSocket().getOutputStream());
            oos.writeObject(message);
            oos.flush();
            System.out.println("消息已转发");
        }else{//不在线存入数据库，等待其上线再转发
            int res = messageService.addMessage(message);
            //数据成功存储
            if(res > 0){
                System.out.println("目标用户离线，消息已暂存");
            }
        }
        return true;
    }

    private void chatToAll(Message message) throws IOException{
        //拿到所有线程
        HashMap<String, Runnable> allThread = ServerThreadManager.getAllThread();
        //遍历线程
        for(Map.Entry<String, Runnable> entry : allThread.entrySet()){
            //将消息转发至其他用户
            if(!entry.getKey().equals(message.getSender())){
                ObjectOutputStream oos = new ObjectOutputStream(((ServerConnection)entry.getValue()).socket.getOutputStream());
                oos.writeObject(message);
                oos.flush();
            }
        }
        System.out.println("转发群发消息完毕");
    }

    private void sendFile(Message message) throws IOException{
        //判断接收文件对象是否在线
        ServerConnection cnn = (ServerConnection)ServerThreadManager.getClientThread(message.getReceiver());
        if(cnn != null){//用户在线
            ObjectOutputStream oos = new ObjectOutputStream(cnn.getSocket().getOutputStream());
            oos.writeObject(message);
            oos.flush();
            System.out.println("文件已转发");
        }else{//用户不在线
            Message mes = new Message();
            mes.setType(MessageType.MESSAGE_SYS_MES);
            mes.setContent("该用户不在线");
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(mes);
            oos.flush();
        }
    }
}
