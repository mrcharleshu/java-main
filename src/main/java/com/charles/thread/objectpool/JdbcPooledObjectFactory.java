package com.charles.thread.objectpool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * org.apache.commons.pool2池对象工具使用
 * https://blog.csdn.net/u012661248/article/details/80491806
 * 利用commons-pool2自定义对象池
 * https://blog.csdn.net/qq447995687/article/details/80233621
 * 一个通用并发对象池的实现
 * http://ifeve.com/generic-concurrent-object-pool/
 * 高并发对象池思考
 * http://blog.spinytech.com/2017/01/10/concurrent_object_pool/
 */
public class JdbcPooledObjectFactory implements PooledObjectFactory<Connection> {
    private final Properties properties;

    public JdbcPooledObjectFactory() {
        this.properties = new Properties();
        this.properties.put("driver", "com.mysql.cj.jdbc.Driver");
        this.properties.put("url", "jdbc:mysql://localhost:3306/purchaser_auth");
        this.properties.put("user", "root");
        this.properties.put("password", "root_pwd");
    }

    @Override
    public PooledObject<Connection> makeObject() throws Exception {
        System.out.println("makeObject");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(PooledObject<Connection> p) throws Exception {
        System.out.println("destroyObject");
        Connection connection = p.getObject();
        connection.close();
    }

    @Override
    public boolean validateObject(PooledObject<Connection> p) {
        System.out.println("validateObject");
        Connection connection = p.getObject();
        try {
            if (connection.isValid(1000)) {
                System.out.println("connection is valid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void activateObject(PooledObject<Connection> p) throws Exception {
        System.out.println("activateObject");
    }

    @Override
    public void passivateObject(PooledObject<Connection> p) throws Exception {
        System.out.println("passivateObject");
    }
}