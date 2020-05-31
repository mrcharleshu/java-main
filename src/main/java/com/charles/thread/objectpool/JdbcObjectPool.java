package com.charles.thread.objectpool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.sql.Connection;

public class JdbcObjectPool {
    private GenericObjectPool<Connection> jdbcPool;

    public JdbcObjectPool(JdbcPooledObjectFactory pooledObjectFactory) {
        GenericObjectPoolConfig<Connection> objectPoolConfig = new GenericObjectPoolConfig<>();
        objectPoolConfig.setMaxTotal(10);
        objectPoolConfig.setMaxIdle(8);
        objectPoolConfig.setMinIdle(3);
        this.jdbcPool = new GenericObjectPool<>(pooledObjectFactory, objectPoolConfig);
    }

    public Connection getConnection() throws Exception {
        return jdbcPool.borrowObject();
    }

    public void returnConnection(Connection connection) {
        jdbcPool.returnObject(connection);
    }

    public void destory(Connection connection) throws Exception {
        jdbcPool.invalidateObject(connection);
    }
}