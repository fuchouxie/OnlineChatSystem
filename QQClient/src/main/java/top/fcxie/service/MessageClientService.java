package top.fcxie.service;

import top.fcxie.common.Message;
import top.fcxie.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {
    /**
     * 与在线用户私聊
     * @param content 私聊内容
     * @param userId 发送者
     * @param getterId 接收者
     */
    public void sendMessageToOne(String content, String userId, String getterId) {
        //构建消息体
        Message message = new Message();
        message.setType(MessageType.MESSAGE_COMM_MES);
        message.setSender(userId);
        message.setContent(content);
        message.setReceiver(getterId);
        message.setSendTime(new Date().toString());
        //发送给服务端
        try {
            //通过线程管理拿到当前用户线程进而拿到与服务端通信的socket
            ObjectOutputStream oos = new ObjectOutputStream(
                    ((ClientConnection)(ClientThreadManager.getClientThread(userId)))
                            .getSocket().getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param content
     * @param userId
     */
    public void sendMessageToAll(String content, String userId) {
        //构建消息体
        Message message = new Message();
        message.setType(MessageType.MESSAGE_ALL_MES);
        message.setSender(userId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        //发送给服务端
        try {
            //通过线程管理拿到当前用户线程进而拿到与服务端通信的socket
            ObjectOutputStream oos = new ObjectOutputStream(
                    ((ClientConnection)(ClientThreadManager.getClientThread(userId)))
                            .getSocket().getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
