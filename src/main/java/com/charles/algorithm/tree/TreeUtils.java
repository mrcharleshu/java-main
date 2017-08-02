package com.charles.algorithm.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.charles.utils.LineSeparators.hyphenSeparator;
import static com.charles.utils.LineSeparators.startSeparator;

class TreeUtils {
    private static final String DELIMITER = "··";
    private static final int HOLDER_LEN = 2;// 二叉树中的值的长度，包括分隔符
    private static final String TREE_DATA_FORMATTER = "%" + HOLDER_LEN + "s";

    static void traverse(TreeNode root) {
        traverse(root, DataPrint.DEFAULT_PRINT);
    }

    static void traverse(TreeNode root, DataPrint dataPrint) {
        startSeparator();
        hyphenSeparator("先序遍历");
        dlrTraverse(root, dataPrint);
        System.out.println();
        hyphenSeparator("中序遍历");
        ldrTraverse(root, dataPrint);
        System.out.println();
        hyphenSeparator("后序遍历");
        lrdTraverse(root, dataPrint);
        System.out.println();
    }

    private static void dlrTraverse(TreeNode node, DataPrint dataPrint) {
        root_left_right(node, dataPrint);
    }

    /**
     * DLR（称为先根次序遍历／先序遍历）
     * 首先访问根，再先序遍历左（右）子树，最后先序遍历右（左）子树
     * @param node
     */
    private static void root_left_right(TreeNode node, DataPrint dataPrint) {
        if (node == null) {
            return;
        }
        dataPrint.print(node.data);
        root_left_right(node.left, dataPrint);
        root_left_right(node.right, dataPrint);
    }

    private static void ldrTraverse(TreeNode node, DataPrint dataPrint) {
        left_root_right(node, dataPrint);
    }

    /**
     * LDR（称为中根次序遍历／中序遍历）
     * 首先中序遍历左（右）子树，再访问根，最后中序遍历右（左）子树
     * @param node
     */
    private static void left_root_right(TreeNode node, DataPrint dataPrint) {
        if (node == null) {
            return;
        }
        ldrTraverse(node.left, dataPrint);
        dataPrint.print(node.data);
        ldrTraverse(node.right, dataPrint);
    }

    private static void lrdTraverse(TreeNode node, DataPrint dataPrint) {
        left_right_root(node, dataPrint);
    }

    /**
     * LRD （称为后根次序遍历／后序遍历）
     * 首先后序遍历左（右）子树，再后序遍历右（左）子树，最后访问根
     * @param node
     */
    static void left_right_root(TreeNode node, DataPrint dataPrint) {
        if (node == null) {
            return;
        }
        left_right_root(node.left, dataPrint);
        left_right_root(node.right, dataPrint);
        dataPrint.print(node.data);
    }

    static List<Point> treeNodeToPoint(TreeNode rootNode) {
        List<Point> points = new ArrayList<>();
        treeNodeToPoint(rootNode, points, 0, -1, rootNode.depth());
        return points;
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

    static void sortTreeNodePoints(List<Point> points) {
        // hyphenSeparator("所有的points信息，排序前");
        // points.forEach(System.out::println);
        // 按照 row,col 对坐标进行排序方便打印
        Collections.sort(points);
        // hyphenSeparator("所有的points信息，排序后");
        // points.forEach(System.out::println);
    }

    static void printTree(TreeNode root) {
        List<Point> points = treeNodeToPoint(root);
        sortTreeNodePoints(points);
        printTree(root.depth(), points);
    }

    static void printTree(int treeDepth, List<Point> points) {
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
        return String.format(TREE_DATA_FORMATTER, data);
    }

    private static int powerOfTwo(int n) {
        if (n == 0) {
            return 1;
        }
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= 2;
        }
        return result;
    }
}
