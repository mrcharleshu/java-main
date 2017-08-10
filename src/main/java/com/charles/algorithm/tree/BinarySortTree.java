package com.charles.algorithm.tree;

import static com.charles.algorithm.tree.TreeUtils.printTree;
import static com.charles.algorithm.tree.TreeUtils.traverse;

/**
 * 功能：
 * 把一个数组的值存入二叉树中（顺序存储方式）
 * 打印树形树结构
 * 然后进行3种方式的遍历
 */
public class BinarySortTree {

    public static void main(String[] args) {
        // int[] array2 = {4, 1, 2, 2, 1, 3, 1, 1, 6, 4, 8, 5, 7, 3, 7, 9, 9};
        int[] array = {54, 38, 76, 62, 38, 26, 40, 39, 83, 50, 98, 80};
        TreeNode rootNode = initBySequence(array);
        BST binaryTree = new BST(rootNode);
        traverse(binaryTree.root);
        printTree(binaryTree.root);
        System.out.println("插入8...");
        binaryTree.root.insert(8);
        printTree(binaryTree.root);
        System.out.println("删除38...");
        binaryTree.delete(38);
        printTree(binaryTree.root);
        System.out.println("删除76...");
        binaryTree.delete(76);
        printTree(binaryTree.root);
        System.out.println("删除54...");
        binaryTree.delete(54);
        printTree(binaryTree.root);
    }

    private static TreeNode initBySequence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new TreeNode();
        }
        TreeNode root = new TreeNode();
        for (int data : arr) {
            root.insert(root, data);
        }
        return root;
    }
}
