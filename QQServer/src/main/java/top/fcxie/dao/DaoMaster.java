package top.fcxie.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoMaster {
    private Connection cnn;

    public Connection getCnn() {
        return cnn;
    }

    public DaoMaster() {
    }

    public void init(){
        try {
            cnn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?userUnicode=true&characterEncoding=GBK", "root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            cnn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
