package top.fcxie.common;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";//登陆成功
    String MESSAGE_LOGIN_FAIL = "2";//登陆失败
    String MESSAGE_COMM_MES = "3";//普通消息
    String MESSAGE_GET_ONLINE_FRIEND = "4";//请求在线好友列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";//返回在线好友列表
    String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出
    String MESSAGE_ALL_MES = "7";//群发消息
    String MESSAGE_CHAT_SUCCESS = "8";//私聊成功
    String MESSAGE_CHAT_FAIL = "9";//私聊失败
    String MESSAGE_SEND_FILE = "10";//文件传输
    String MESSAGE_SYS_MES = "11";//系统消息
}
