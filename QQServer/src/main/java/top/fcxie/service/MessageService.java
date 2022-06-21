package top.fcxie.service;


import top.fcxie.common.Message;
import top.fcxie.dao.MessageDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageDao messageDao = MessageDao.getInstance();

    /**
     * 添加离线消息
     * @param message
     * @return
     */
    public int addMessage(Message message){
        if(message == null) return -1;
        int res = -1;
        try {
            res = messageDao.insertMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取该用户未接收的离线消息
     * @param getterId
     * @return
     */
    public List<Message> getMessageByGetterId(String getterId){
        if(getterId == null || getterId.equals("")) return null;
        List<Message> lists = new ArrayList<>();
        try {
            lists = messageDao.selectMessage(getterId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 更新该用户的离线消息发送状态
     * @param getterId
     * @return
     */
    public int updateMessageStatusByGetterId(String getterId){
        if(getterId == null || getterId.equals("")) return -1;
        int res = -1;
        try {
            res = messageDao.updateMessageStatus(getterId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
