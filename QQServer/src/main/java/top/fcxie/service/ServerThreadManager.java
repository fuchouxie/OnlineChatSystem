package top.fcxie.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理与客户端连接的线程
 */
public class ServerThreadManager {
    private static HashMap<String, Runnable> map = new HashMap<>();

    public static void addClientThread(String username, ServerConnection thread){
        map.put(username, thread);
    }

    public static Runnable getClientThread(String username){
        return map.get(username);
    }

    public static String getClientList(){
        StringBuilder builder = new StringBuilder();
        for(Map.Entry entry : map.entrySet()){
            builder.append(entry.getKey());
            builder.append(",");
        }
        return builder.toString();
    }

    public static HashMap<String, Runnable> getAllThread(){
        return map;
    }

    public static void removeClientThread(String username){
        map.remove(username);
    }
}
