package com.charles.algorithm.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.charles.algorithm.tree.TreeUtils.printTree;
import static com.charles.algorithm.tree.TreeUtils.traverse;

/**
 * 功能：
 * 把一个数组的值存入二叉树中（链表存储方式／顺序存储方式）
 * 打印树形树结构
 * 然后进行3种方式的遍历
 */
public class BinaryTree {

    TreeNode root;

    public BinaryTree(TreeNode rootNode) {
        this.root = rootNode;
    }

    void delete(Integer data) {
        root = delete(root, data);
    }

    /**
     * 一、删除节点的左右子树都存在
     * 1、左节点是当前左子树的最大节点，此时只需要用左节点代替根节点即可
     * 2、左节点不是当前左子树的最大节点，左侧的最大节点代替删除的节点即可
     * <p>
     * 二、普通节点的删除
     * 删除的节点没有左子树，也没有右子树，直接删除就好
     * 删除的节点有左子树，没有右子树，用左子树替换自己
     * 删除的节点左子树为空，右子树节点不为空，用右子树替换自己
     * 删除的节点左右子树均不为空，不过又要分为两种情形：
     * 1、左节点是删除节点左侧的最大节点（和根结点一样）
     * 2、左节点不是删除节点左侧的最大节点（和根结点一样）
     */
    private TreeNode delete(TreeNode node, Integer data) {
        if (node == null) {
            return null;
        }
        int compare = data.compareTo(node.data);
        if (compare < 0) {
            node.left = delete(node.left, data);
        } else if (compare > 0) {
            node.right = delete(node.right, data);
        } else {
            if (node.right == null) {
                return node.left;  // 被删除的节点没有右子树则返回左子树替代被删除节点
            }
            if (node.left == null) {
                return node.right; // 被删除的节点没有左子树则返回右子树替代被删除节点
            }
            TreeNode temp = node;
            // 左边查询最大的，删除最大节点父节点的右引用
            node = max(node.left);
            node.left = deleteMax(temp.left);
            node.right = temp.right;
            // 右边查询最小的，删除最小节点父节点的左引用
            // node = min(node.right);            // 找到被删除节点右子树上最小节点(没有左子树)替代被删除节点
            // node.right = deleteMin(temp.right);// 将被删除节点原来的右子树去掉最小节点后作为替代节点的右子树
            // node.left = temp.left;             // 将被删除节点原来的左子树作为替代节点的左子树
        }
        return node;
    }

    private TreeNode deleteMax(TreeNode node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        return node;
    }

    private TreeNode max(TreeNode node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    private TreeNode deleteMin(TreeNode node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    private TreeNode min(TreeNode node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public static void main(String[] args) {
        sequenceTree();
        // sortedTree();
    }

    private static void sequenceTree() {
        // 满二叉树
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        // int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // 完全二叉树（只要有8就可以，9 - 15 任选）
        // int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        // 链表存储方式
        TreeNode rootNode = initByChainTable(array);
        BinaryTree binaryTree = new BinaryTree(rootNode);
        traverse(binaryTree.root);
        printTree(binaryTree.root);
    }

    private static void sortedTree() {
        // 顺序存储方式
        // int[] array2 = {4, 1, 2, 2, 1, 3, 1, 1, 6, 4, 8, 5, 7, 3, 7, 9, 9};
        int[] array = {54, 38, 76, 62, 38, 26, 40, 39, 83, 50, 98, 80};
        TreeNode rootNode = initBySequence(array);
        BinaryTree binaryTree = new BinaryTree(rootNode);
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