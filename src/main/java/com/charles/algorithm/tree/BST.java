package com.charles.algorithm.tree;

/**
 * 二叉排序树（Binary Sort Tree）
 */
class BST {

    TreeNode root;

    BST(TreeNode rootNode) {
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

}