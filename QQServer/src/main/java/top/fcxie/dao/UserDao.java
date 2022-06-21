package top.fcxie.dao;

import top.fcxie.common.User;

import java.sql.*;

public class UserDao {
    DaoMaster master = new DaoMaster();
    private static UserDao instance = new UserDao();
    public static UserDao getInstance(){
        return instance;
    }

    /**
     * 插入一个用户
     * @param user
     * @return
     * @throws Exception
     */
    public int insertUser(User user) throws Exception{
        String sql = "insert into user(username,password) values(?,?)";
        master.init();
        PreparedStatement  statement = master.getCnn().prepareStatement(sql);
        statement.setString(1,user.getUserName());
        statement.setString(2,user.getPassword());
        int res = statement.executeUpdate();
        statement.close();
        master.close();
        return res;
    }

    /**
     * 查询根据用户名用户
     * @param username
     * @return
     * @throws Exception
     */
    public User queryUser(String username) throws Exception{
        String sql = "select * from user where username = '"+username+"'";
        User user = new User();
        master.init();
        Statement statement =  master.getCnn().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            user.setUserName(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        }
        resultSet.close();
        statement.close();
        master.close();
        return user;
    }

    /**
     * 更新用户密码
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUser(User user) throws Exception{
        String sql = "update user set password = "+user.getPassword()+" where username = '"+user.getUserName()+"'";
        master.init();
        Statement statement =  master.getCnn().createStatement();
        int res = statement.executeUpdate(sql);
        statement.close();
        master.close();
        return res;
    }

    /**
     * 删除用户
     * @param username
     * @return
     * @throws Exception
     */
    public int deleteUser(String username) throws Exception{
        String sql = "delete from user where username = '"+username+"'";
        master.init();
        PreparedStatement  statement =  master.getCnn().prepareStatement(sql);
        int res = statement.executeUpdate(sql);
        statement.close();
        master.close();
        return res;
    }



}
