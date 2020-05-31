package com.charles.thread.objectpool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 如果一个类被频繁请求使用，那么不必每次都生成一个实例，可以将这个类都一些实例保存到一个“池”中，
 * 待需要使用的时候直接从“池”中获取。这个“池”就被称为对象池，它可以是一个数组，一个链表或者任何集合。
 * <p>
 * 对象池其实就是一个集合，里面包含了我们需要的对象集合，当然这些对象都被池化了，也就是被对象池所管理，
 * 想要这样的对象，从池子里取个就行，但是用完得归还。对象池的对象最好是创建比较费时的大对象，
 * 如果是太简单的对象，再进入池化的时间比自己构建还多，就不划算了。
 * 可以理解对象池为单例模式的延展，多例模式，就那么几个对象实例，再多没有了。
 * @param <T>
 */
public class SmPooledObjectFactory<T> implements PooledObjectFactory<T> {
    private static final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public PooledObject<T> makeObject() throws Exception {
        T obj = (T) Integer.valueOf(counter.getAndIncrement());
        System.out.println("create T " + obj);
        return new DefaultPooledObject<T>(obj);
    }

    @Override
    public void destroyObject(PooledObject<T> pooledObject) throws Exception {
        System.out.println("Destroy T " + pooledObject);
    }

    @Override
    public boolean validateObject(PooledObject<T> pooledObject) {
        return false;
    }

    @Override
    public void activateObject(PooledObject<T> pooledObject) throws Exception {
        System.out.println("Before borrow " + pooledObject);
    }

    @Override
    public void passivateObject(PooledObject<T> pooledObject) throws Exception {
        System.out.println("return " + pooledObject);
    }

}