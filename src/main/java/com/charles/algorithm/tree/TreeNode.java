package com.charles.algorithm.tree;

class TreeNode {
    TreeNode left;
    TreeNode right;
    Integer data;
    int count = 1;//默认重复为1

    TreeNode() {
        this.data = -1;
    }

    TreeNode(int data) {
        this.data = data;
    }

    int size() {
        return size(this);
    }

    private int size(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return size(node.left) + size(node.right) + 1;
    }

    int depth() {
        return depth(this);
    }

    int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return depth(root, 0);
    }

    private int depth(TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }
        depth++;
        int left = depth(node.left, depth);
        int right = depth(node.right, depth);
        return Math.max(left, right);
    }

    void insert(int data) {
        insert(this, data);
    }

    void insert(TreeNode root, int data) {
        if (root.data < 0) {    // root
            root.data = data;
        } else {
            TreeNode leaf = new TreeNode(data);
            insert(root, leaf);
        }
    }

    private void insert(TreeNode node, TreeNode leaf) {
        // 叶子节点和node节点比，小于等于放左边，大于放右边
        int compare = leaf.data.compareTo(node.data);
        if (compare < 0) {             // < 小于根节点在左子树上添加
            if (node.left == null) {
                node.left = leaf;
            } else {
                insert(node.left, leaf);
            }
        } else if (compare > 0) {      // > 大于根节点在右子树上添加
            if (node.right == null) {
                node.right = leaf;
            } else {
                insert(node.right, leaf);
            }
        } else {
            node.count++;
        }
    }
}