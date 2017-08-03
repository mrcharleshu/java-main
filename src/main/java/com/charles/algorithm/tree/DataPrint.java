package com.charles.algorithm.tree;

/**
 * 元素访问器接口(实现元素访问操作的解耦合)
 * 可以自定义输出规则
 */
@FunctionalInterface
public interface DataPrint {

    String SPACE = " ";
    String BRACKET_LEFT = "[";
    String BRACKET_RIGHT = "]";
    String ARROW = SPACE + ">" + SPACE;

    void print(TreeNode node);

    DataPrint DEFAULT_PRINT = node -> System.out.print(node.data +
            (node.count > 1 ? (BRACKET_LEFT + node.count + BRACKET_RIGHT + SPACE) : SPACE));
}