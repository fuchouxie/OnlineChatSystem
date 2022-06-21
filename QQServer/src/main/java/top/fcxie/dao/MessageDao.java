package top.fcxie.dao;

import top.fcxie.common.Message;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MessageDao {
    private DaoMaster master = new DaoMaster();
    private static MessageDao instance = new MessageDao();
    private Connection cnn;
    public static MessageDao getInstance(){
        return instance;
    }

    /**
     * 插入一条信息
     * @param message
     * @return
     */
    public int insertMessage(Message message) throws SQLException {
        String sql = "insert into message(getter_id,senter_id,content,time,type,status) " +
                "values(?,?,?,?,?,?)";
        master.init();
        PreparedStatement statement = master.getCnn().prepareStatement(sql);
        statement.setString(1,message.getReceiver());
        statement.setString(2,message.getSender());
        statement.setString(3,message.getContent());
        statement.setString(4,message.getSendTime());
        statement.setString(5,message.getType());
        statement.setInt(6,0);
        int res = statement.executeUpdate();
        master.close();
        return res;
    }

    /**
     * 查看该用户未接收的离线消息
     * @param userId
     * @return
     */
    public List<Message> selectMessage(String userId) throws SQLException {
        String sql = "select * from message where getter_id = '"+userId+"' and status = "+"0";
        List<Message> messageList = new LinkedList<>();
        master.init();
        Statement statement =  master.getCnn().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            Message message = new Message();
            message.setSender(resultSet.getString("senter_id"));
            message.setReceiver(resultSet.getString("getter_id"));
            message.setContent(resultSet.getString("content"));
            message.setType(resultSet.getString("type"));
            message.setSendTime(resultSet.getString("time"));
            messageList.add(message);
        }
        resultSet.close();
        statement.close();
        master.close();
        return messageList;

    }

    /**
     * 更新该用户离线消息状态
     * @param getterId
     * @return
     */
    public int updateMessageStatus(String getterId) throws SQLException{
        String sql = "update message set status = "+1+" where getter_id = '"+getterId+"'";
        master.init();
        Statement statement =  master.getCnn().createStatement();
        int res = statement.executeUpdate(sql);
        statement.close();
        master.close();
        return res;
    }

}
