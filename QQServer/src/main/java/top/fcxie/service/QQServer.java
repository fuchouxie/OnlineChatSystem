package top.fcxie.service;

import top.fcxie.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * 服务器端
 */
public class QQServer {
    private ServerSocket socket = null;
    private UserService userService = new UserService();
    private MessageService messageService = new MessageService();
    public QQServer() {
        System.out.println("服务器端已启动....");
        try {
            socket = new ServerSocket(8888);
            new Thread(new SystemService()).start();
            //循环监听，每当有一个客户端发起连接就创建一个连接并继续监听
            while (true){
                Socket connect = socket.accept();
                System.out.println("一个用户正在发起连接....");
                //读取客户端发送的用户信息并进行验证
                ObjectInputStream ois = new ObjectInputStream(connect.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(connect.getOutputStream());
                User user = (User) ois.readObject();//读取用户信息
                User target = new User();//数据库查询的用户信息
                Message message = new Message();//消息体
                //登陆验证
                if((target = userService.getUser(user.getUserName()))!= null && user.getPassword().equals(target.getPassword())){
                    System.out.println("用户："+user.getUserName()+"登录成功!");
                    //返回登陆成功给客户端
                    message.setType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(message);
                    oos.flush();
                    //将离线暂存消息转发给用户
                    //查询当前用户的离线消息
                    List<Message> list = messageService.getMessageByGetterId(user.getUserName());
                    //循环转发消息
                    for(Message mes : list){
                        oos = new ObjectOutputStream(connect.getOutputStream());
                        oos.writeObject(mes);
                        oos.flush();
                    }
                    //修改离线消息状态为已发送
                    messageService.updateMessageStatusByGetterId(user.getUserName());
                    //创建一个子线程去控制与客户端的连接
                    ServerConnection serverConnection = new ServerConnection(connect, user.getUserName());
                    new Thread(serverConnection).start();
                    //将线程加入连接池
                    ServerThreadManager.addClientThread(user.getUserName(),serverConnection);

                }else{
                    System.out.println("用户："+user.getUserName()+"登录失败!");
                    //返回消息通知客户端登录失败
                    message.setType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    connect.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
