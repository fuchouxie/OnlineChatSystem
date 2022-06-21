package top.fcxie.service;

import java.util.HashMap;

/**
 * 管理客户端线程
 */
public class ClientThreadManager {
    private static HashMap<String, Runnable> map = new HashMap<>();

    public static void addClientThread(String username, ClientConnection thread){
        map.put(username, thread);
    }

    public static Runnable getClientThread(String username){
        return map.get(username);
    }


}
