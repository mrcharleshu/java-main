package com.charles.algorithm.tree;

import static com.charles.algorithm.tree.TreeUtils.printTree;

/**
 * 排序二叉树
 */
public class DeleteNode {

    static class BST {
        private TreeNode root;  // 排序二叉树的根节点

        public BST() {
            this.root = new TreeNode();
        }

        /**
         * 删除树节点
         * @param t 待删除的元素
         */
        public void delete(Integer t) {
            root = delete(root, t);
        }

        private TreeNode delete(TreeNode x, Integer t) {
            if (x == null) {
                return null;
            }
            int cmp = t.compareTo(x.data);
            if (cmp < 0) {
                x.left = delete(x.left, t);
            } else if (cmp > 0) {
                x.right = delete(x.right, t);
            } else {
                if (x.right == null) {
                    return x.left;  // 被删除的节点没有右子树则返回左子树替代被删除节点
                }
                if (x.left == null) {
                    return x.right; // 被删除的节点没有左子树则返回右子树替代被删除节点
                }
                TreeNode temp = x;
                x = min(x.right);   // 找到被删除节点右子树上最小节点(没有左子树)替代被删除节点
                x.right = deleteMin(temp.right);    // 将被删除节点原来的右子树去掉最小节点后作为替代节点的右子树
                x.left = temp.left;     // 将被删除节点原来的左子树作为替代节点的左子树
            }
            return x;
        }

        private TreeNode deleteMin(TreeNode x) {
            if (x.left == null) {
                return x.right;
            }
            x.left = deleteMin(x.left);
            return x;
        }

        private TreeNode min(TreeNode x) {
            if (x.left == null) {
                return x;
            }
            return min(x.left);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {54, 38, 76, 62, 38, 26, 40, 83, 50, 98, 80};
        BST tree = new BST();
        for (int i = 0; i < a.length; i++) {
            tree.root.insert(a[i]);
        }
        printTree(tree.root);
        // traverse(tree.root, t -> System.out.print(t + DataPrint.ARROW));
        tree.root.insert(55);
        printTree(tree.root);
        // traverse(tree.root, t -> System.out.print(t + DataPrint.ARROW));
        tree.delete(54);
        printTree(tree.root);
        // traverse(tree.root, t -> System.out.print(t + DataPrint.ARROW));
        tree.delete(38);
        printTree(tree.root);
        // traverse(tree.root, t -> System.out.print(t + DataPrint.ARROW));
    }
}