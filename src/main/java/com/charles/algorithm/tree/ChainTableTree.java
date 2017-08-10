package com.charles.algorithm.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.charles.algorithm.tree.TreeUtils.printTree;
import static com.charles.algorithm.tree.TreeUtils.traverse;
import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 链表存储方式
 */
public class ChainTableTree {

    public static void main(String[] args) {
        // 满二叉树
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        // int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // 完全二叉树（只要有8就可以，9 - 15 任选）
        // int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        // 链表存储方式
        TreeNode rootNode = initByChainTable(array);
        traverse(rootNode, node -> System.out.print(node.data + Printer.ARROW));
        hyphenSeparator();
        traverse(rootNode);
        printTree(rootNode);
    }

    private static TreeNode initByChainTable(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new TreeNode();
        }
        List<TreeNode> nodeList = new LinkedList<>();
        // 将一个数组的值依次转换为Node节点
        Arrays.stream(arr).forEach(node -> nodeList.add(new TreeNode(node)));
        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < arr.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).left = nodeList.get(parentIndex * 2 + 1);
            printCreateLeftChild(parentIndex);
            // 右孩子
            nodeList.get(parentIndex).right = nodeList.get(parentIndex * 2 + 2);
            printCreateRightChild(parentIndex);
        }
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = arr.length / 2 - 1;
        // 左孩子
        nodeList.get(lastParentIndex).left = nodeList.get(lastParentIndex * 2 + 1);
        printCreateLeftChild(lastParentIndex);
        // 右孩子,如果数组的长度为奇数才建立右孩子
        if (arr.length % 2 == 1) {
            nodeList.get(lastParentIndex).right = nodeList.get(lastParentIndex * 2 + 2);
            printCreateRightChild(lastParentIndex);
        }
        // nodeList中第0个索引处的值即为根节点
        return nodeList.get(0);
    }

    private static void printCreateLeftChild(int parentIndex) {
        System.out.println(String.format("根节点index为 %s 的左节点index为 %s", parentIndex, parentIndex * 2 + 1));
    }

    private static void printCreateRightChild(int parentIndex) {
        System.out.println(String.format("根节点index为 %s 的右节点index为 %s", parentIndex, parentIndex * 2 + 2));
    }
}
