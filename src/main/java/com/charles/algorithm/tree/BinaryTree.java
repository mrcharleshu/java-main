package com.charles.algorithm.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.charles.algorithm.tree.TreeUtils.*;
import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 功能：
 * 把一个数组的值存入二叉树中（链表存储方式／顺序存储方式）
 * 打印树形树结构
 * 然后进行3种方式的遍历
 */
public class BinaryTree {

    public static void main(String[] args) {
        // 满二叉树
        // int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // 完全二叉树（只要有8就可以，9-15任选）
        // int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        // int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        BinaryTree binaryTree = new BinaryTree();
        // 链表存储方式
        // TreeNode rootNode = binaryTree.initByChainTable(array1);
        // 顺序存储方式
        // int[] array2 = {4, 1, 2, 2, 1, 3, 1, 1, 6, 4, 8, 5, 7, 3, 7, 9, 9};
        int[] array2 = {54, 38, 76, 62, 38, 26, 40, 83, 50, 98, 80};
        TreeNode rootNode = binaryTree.initBySequence(array2);
        traverse(rootNode);
        rootNode.insert(8);
        traverse(rootNode);
        hyphenSeparator();
        printTree(rootNode);
    }

    public TreeNode initByChainTable(int[] arr) {
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

    private void printCreateLeftChild(int parentIndex) {
        System.out.println(String.format("根节点index为 %s 的左节点index为 %s", parentIndex, parentIndex * 2 + 1));
    }

    private void printCreateRightChild(int parentIndex) {
        System.out.println(String.format("根节点index为 %s 的右节点index为 %s", parentIndex, parentIndex * 2 + 2));
    }

    private TreeNode initBySequence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new TreeNode();
        }
        TreeNode root = new TreeNode();
        for (int i = 0; i < arr.length; i++) {
            root.insert(root, arr[i]);
        }
        return root;
    }
}