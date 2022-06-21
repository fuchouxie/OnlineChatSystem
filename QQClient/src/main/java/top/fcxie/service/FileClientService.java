package top.fcxie.service;

import top.fcxie.common.Message;
import top.fcxie.common.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class FileClientService {

    /**
     * 发送文件给在线用户
     * @param src
     * @param dest
     * @param userId
     * @param getterId
     */
    public void sendFileToOne(String src, String dest, String userId, String getterId) {
        try {
            //构建消息体
            Message message = new Message();
            message.setSender(userId);
            message.setReceiver(getterId);
            message.setSendTime(new Date().toString());
            message.setDest(dest);
            message.setSrc(src);
            message.setType(MessageType.MESSAGE_SEND_FILE);
            File file = new File(src);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] fileBytes = new byte[(int)file.length()];
            fileInputStream.read(fileBytes);//读取文件到字节数组
            message.setFile(fileBytes);
            //发送给服务端进行转发
            ClientConnection clientThread = (ClientConnection) ClientThreadManager.getClientThread(userId);
            ObjectOutputStream oos = new ObjectOutputStream(clientThread.getSocket().getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
