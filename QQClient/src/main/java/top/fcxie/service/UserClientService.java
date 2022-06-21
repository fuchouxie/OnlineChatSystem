package top.fcxie.service;


import top.fcxie.common.Message;
import top.fcxie.common.User;
import top.fcxie.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 用户客户端服务类
 */
public class UserClientService {
    private User user = new User();//组合User
    private Socket connection;

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public boolean login(String username, String password){
        boolean isLogin = false;
        //创建用户
        user.setUserName(username);
        user.setPassword(password);

        try {

            //连接服务器端
            connection = new Socket(InetAddress.getByName("127.0.0.1"), 8888);
            ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
            //传递用户信息给服务器端
            oos.writeObject(user);
            oos.flush();
            //获取服务器返回结果
            ObjectInputStream ooi = new ObjectInputStream(connection.getInputStream());
            Message message = (Message) ooi.readObject();
            //判断消息类型
            if(message.getType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                //登陆成功，创建一个子线程保持与服务端的链接
                ClientConnection clientConnection = new ClientConnection(connection);
                new Thread(clientConnection).start();
                //将线程加入池子中
                ClientThreadManager.addClientThread(username, clientConnection);
                isLogin = true;
            }else{
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isLogin;
    }

    /**
     * 获取在线用户
     */
    public void onlineFriendList(){
        //构建请求消息
        Message mess = new Message();
        mess.setType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        mess.setSender(user.getUserName());
        //拿到当前客户端线程
        ClientConnection conn = (ClientConnection) ClientThreadManager.getClientThread(user.getUserName());
        //拿到客户端线程中的socket
        Socket socket = conn.getSocket();
        try {
            //获取对象流
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(mess);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出
     */
    public void logout() {
        //发送消息通知服务端
        Message mes = new Message();
        mes.setSender(user.getUserName());
        mes.setType(MessageType.MESSAGE_CLIENT_EXIT);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(mes);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
