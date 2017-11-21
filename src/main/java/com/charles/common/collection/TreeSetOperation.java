package com.charles.common.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeSet;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * TreeSet是二叉树实现的,TreeSet中的数据是自动排好序的，不允许放入null值
 * HashSet是哈希表实现的,HashSet中的数据是无序的，可以放入null，但只能放入一个null，两者中的值都不能重复
 */
public class TreeSetOperation {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeSetOperation.class);
    private final TreeSet<Double> set = new TreeSet<>();

    public TreeSet<Double> getSet() {
        return set;
    }

    private void add(Double value) {
        System.out.println(set.add(value));
    }

    public static void main(String[] args) {
        TreeSetOperation operation = new TreeSetOperation();
        // set.add(null); //不能为空，没有compare方法去比较元素的大小
        operation.add(1.2);
        operation.add(34.45);
        operation.add(3.54);
        operation.add(15.76);
        operation.add(5.9);
        operation.add(12.41);
        operation.add(3.54);//有重复返回false
        hyphenSeparator();
        TreeSet<Double> set = operation.getSet();
        System.out.println(set);
        hyphenSeparator();
        LOGGER.info("set.first() = {}", set.first());
        LOGGER.info("set.last() = {}", set.last());
        // poll取出并删除
        // LOGGER.info("set.pollFirst() = {}", set.pollFirst());
        // LOGGER.info("set.pollLast() = {}", set.pollLast());
        LOGGER.info("set.lower() = {}", set.lower(5d));
        LOGGER.info("set.higher() = {}", set.higher(6d));
        // Returns the least element in this set greater than or equal to the given element
        LOGGER.info("set.ceiling() = {}", set.ceiling(10d));
        LOGGER.info("set.subSet() = {}", set.subSet(3d, 8d));
        LOGGER.info("set.headSet() = {}", set.headSet(5d));
        LOGGER.info("set.tailSet() = {}", set.tailSet(6d));
    }
}
