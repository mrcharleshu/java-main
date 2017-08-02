package com.charles.algorithm.tree;

/**
 * 元素访问器接口(实现元素访问操作的解耦合)
 * 可以自定义输出规则
 */
@FunctionalInterface
public interface DataPrint {

    String SPACE = " ";
    String ARROW = SPACE + ">" + SPACE;

    void print(int t);

    DataPrint DEFAULT_PRINT = data -> System.out.print(data + SPACE);
}