package com.charles.algorithm;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;

/**
 * 功能：
 * 把一个数组的值存入二叉树中（链表存储方式／顺序存储方式）
 * 打印树形树结构
 * 然后进行3种方式的遍历
 */
public class BinaryTree {
    private static final String SPACE = " ";
    private static final String DELIMITER = "··";
    private static final int HOLDER_LEN = 2;// 二叉树中的值的长度，包括分隔符

    private static class TreeNode {
        TreeNode left;
        TreeNode right;
        int data;

        TreeNode() {
        }

        TreeNode(int data) {
            this.left = null;
            this.right = null;
            this.data = data;
        }
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
        TreeNode root = new TreeNode();
        if (arr == null || arr.length == 0) {
            return root;
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                root.data = arr[i];
            } else {
                TreeNode leaf = new TreeNode(arr[i]);
                sequentialInsert(root, leaf);
            }
        }
        return root;
    }

    static void sequentialInsert(TreeNode root, TreeNode leaf) {
        if (leaf.data <= root.data) { // <= 放左边
            if (root.left == null) {
                root.left = leaf;
            } else {
                sequentialInsert(root.left, leaf);
            }
        } else {                          // < 放右边
            if (root.right == null) {
                root.right = leaf;
            } else {
                sequentialInsert(root.right, leaf);
            }
        }
    }

    private static void threeKindTreeSort(TreeNode root) {
        hyphenSeparator("先序遍历");
        preOrderTraverse(root);
        System.out.println();
        hyphenSeparator("中序遍历");
        midOrderTraverse(root);
        System.out.println();
        hyphenSeparator("后序遍历");
        postOrderTraverse(root);
    }

    /**
     * DLR（称为先根次序遍历／先序遍历）
     * 首先访问根，再先序遍历左（右）子树，最后先序遍历右（左）子树
     * @param node
     */
    public static void preOrderTraverse(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + SPACE);
        preOrderTraverse(node.left);
        preOrderTraverse(node.right);
    }

    /**
     * LDR（称为中根次序遍历／中序遍历）
     * 首先中序遍历左（右）子树，再访问根，最后中序遍历右（左）子树
     * @param node
     */
    public static void midOrderTraverse(TreeNode node) {
        if (node == null) {
            return;
        }
        midOrderTraverse(node.left);
        System.out.print(node.data + SPACE);
        midOrderTraverse(node.right);
    }

    /**
     * LRD （称为后根次序遍历／后序遍历）
     * 首先后序遍历左（右）子树，再后序遍历右（左）子树，最后访问根
     * @param node
     */
    public static void postOrderTraverse(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderTraverse(node.left);
        postOrderTraverse(node.right);
        System.out.print(node.data + SPACE);
    }

    private static int getTreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return depthRecursion(root, 0);
    }

    private static int depthRecursion(TreeNode node, int floor) {
        if (node == null) {
            return floor;
        }
        floor++;
        int left = depthRecursion(node.left, floor);
        int right = depthRecursion(node.right, floor);
        return Math.max(left, right);
    }

    static class Point implements Comparable<Point> {

        public Point(int row, int col, int data) {
            this.row = row;
            this.col = col;
            this.data = data;
        }

        int row = 0;
        int col = 0;
        int data = -1;

        @Override
        public String toString() {
            return "Point{" + "row=" + row + ", col=" + col + ", data=" + data + '}';
        }

        @Override
        public int compareTo(Point o) {
            if (this.row == o.row) {
                if (col == o.col)
                    return 0;
                else if (col < o.col) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (row < o.row) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    static void treeNodeToPoint(TreeNode root, List<Point> points, int row, int col, int depth) {
        if (root == null) {
            return;
        }
        int gap = HOLDER_LEN * (powerOfTwo(depth - 1) - 1);
        if (col == -1) {//root
            col = gap;
        }
        points.add(new Point(row, col, root.data));
        row++;
        depth--;
        treeNodeToPoint(root.left, points, row, col - ((gap - 1) / 2) - HOLDER_LEN, depth);
        treeNodeToPoint(root.right, points, row, col + (gap - 1) / 2 + HOLDER_LEN, depth);
    }

    static int powerOfTwo(int n) {
        if (n == 0) {
            return 1;
        }
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= 2;
        }
        return result;
    }

    private static void printTree(int treeDepth, List<Point> points) {
        // 按每点 5个字符宽进行打印（需要进行坐标系转换）， 当data==-1时 显示 *
        int row = 0;
        StringBuilder sb = new StringBuilder();
        int treeNodeLength = HOLDER_LEN * (powerOfTwo(treeDepth) - 1);
        hyphenSeparator("树形打印二叉树");
        for (Point point : points) {
            if (row == point.row) {
                sb.append(printDelimiter(point.col - sb.length()));
                sb.append(printData(point.data));
            } else {
                if (sb.length() < treeNodeLength) {
                    sb.append(printDelimiter(treeNodeLength - sb.length()));
                }
                System.out.println(sb.toString());
                row++;
                sb = new StringBuilder();
                sb.append(printDelimiter(point.col - sb.length()));
                sb.append(printData(point.data));
            }
        }
        if (sb.length() < treeNodeLength) {
            sb.append(printDelimiter(treeNodeLength - sb.length()));
        }
        System.out.println(sb.toString());
    }

    private static String printDelimiter(int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times / HOLDER_LEN; i++) {
            sb.append(DELIMITER);
        }
        return sb.toString();
    }

    private static String printData(int data) {
        return String.format("%" + HOLDER_LEN + "s", data);
    }

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
        int[] array2 = {4, 1, 2, 2, 1, 3, 6, 4, 8, 5, 7, 3, 7, 8, 9};
        TreeNode rootNode = binaryTree.initBySequence(array2);
        int treeDepth = getTreeDepth(rootNode);
        hyphenSeparator();
        System.out.println("Tree Depth = " + treeDepth);
        List<Point> points = Lists.newArrayList();
        treeNodeToPoint(rootNode, points, 0, -1, treeDepth);
        hyphenSeparator("所有的points信息，排序前");
        points.forEach(System.out::println);
        // 按照 row,col 对 坐标进行排序方便打印
        Collections.sort(points);
        hyphenSeparator("所有的points信息，排序后");
        points.forEach(System.out::println);
        printTree(treeDepth, points);
        // 三种排序方式
        threeKindTreeSort(rootNode);
    }
}