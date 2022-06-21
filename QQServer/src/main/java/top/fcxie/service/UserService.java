package top.fcxie.service;

import top.fcxie.common.User;
import top.fcxie.dao.UserDao;

import java.sql.ResultSet;

/**
 *  用户数据库操作服务
 */
public class UserService {
    private UserDao userDao = UserDao.getInstance();

    /**
     * 添加一个用户
     * @param user
     * @return
     */
    public int addUser(User user){
        if(user == null) return -1;
        int result = -1;
        try {
            result = userDao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取用户
     * @param username
     * @return
     */
    public User getUser(String username){
        User user = new User();
        try {
            user  = userDao.queryUser(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int removeUser(String username){
        User user = new User();
        int result = -1;
        try {
            result = userDao.deleteUser(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

}
