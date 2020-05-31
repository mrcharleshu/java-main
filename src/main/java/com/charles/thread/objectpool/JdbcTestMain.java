package com.charles.thread.objectpool;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class JdbcTestMain {
    public static JdbcPooledObjectFactory pooledObject = new JdbcPooledObjectFactory();
    public static JdbcObjectPool jdbcObjectPool = new JdbcObjectPool(pooledObject);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new JdbcThread());
            thread.start();
        }
    }

    private static class JdbcThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                Connection connection;
                try {
                    connection = jdbcObjectPool.getConnection();
                    String sql = "select count(*) as cnt from t_auth_auto_config";
                    CallableStatement statement = connection.prepareCall(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt("cnt"));
                    }
                    TimeUnit.SECONDS.sleep(2);
                    jdbcObjectPool.returnConnection(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}