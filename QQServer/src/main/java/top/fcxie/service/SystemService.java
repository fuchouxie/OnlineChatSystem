package top.fcxie.service;

import top.fcxie.common.Message;
import top.fcxie.common.MessageType;
import top.fcxie.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统广播服务
 */
public class SystemService implements Runnable{
    @Override
    public void run() {
        while (true){
            System.out.println("请输入系统通知内容，输入exit退出");
            String s = Utility.readString(100);
            if(s.equals("exit")){
                break;
            }
            Message message = new Message();
            message.setSender("系统");
            message.setType(MessageType.MESSAGE_ALL_MES);
            message.setContent(s);
            HashMap<String, Runnable> allThread = ServerThreadManager.getAllThread();
            for(Map.Entry<String, Runnable> entry : allThread.entrySet()){
                try {
                    //获取每个客户端线程的输出流
                    ObjectOutputStream oos = new ObjectOutputStream(((ServerConnection)entry.getValue()).getSocket().getOutputStream());
                    oos.writeObject(message);
                    oos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
