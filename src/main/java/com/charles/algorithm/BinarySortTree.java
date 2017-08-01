package com.charles.algorithm;

/**
 * 排序二叉树
 * 红黑树（Red Black Tree） 是一种自平衡二叉查找树
 */
public class BinarySortTree {

    interface Visitor<T> {
        void visit(T t);
    }

    /**
     * 排序二叉树
     * @param <T> 泛型参数 树节点存储的元素的类型
     */
    static class BST<T extends Comparable<T>> {
        private TreeNode root;  // 排序二叉树的根节点

        /**
         * 描述树节点结构的内部类
         */
        private class TreeNode {
            private T value;            // 树节点存储的元素
            private TreeNode left, right;   // 指向左右孩子节点的引用

            public TreeNode(T value) {
                this.value = value;
            }
        }

        /**
         * 返回树的节点个数
         * @return
         */
        public int size() {
            return size(root);
        }

        private int size(TreeNode x) {
            if (x == null) {
                return 0;
            }
            return size(x.left) + size(x.right) + 1;
        }

        /**
         * 添加树节点
         * @param t 待添加的元素
         */
        public void add(T t) {
            root = add(root, t);
        }

        private TreeNode add(TreeNode x, T t) {
            if (x == null) {
                return new TreeNode(t); // 如果当前根节点为空 则创建新节点作为根节点
            }
            int cmp = t.compareTo(x.value);
            if (cmp < 0) {
                x.left = add(x.left, t);    // 小于根节点在左子树上添加
            } else if (cmp > 0) {
                x.right = add(x.right, t);  // 大于根节点在右子树上添加
            }
            return x;
        }

        /**
         * 删除树节点
         * @param t 待删除的元素
         */
        public void delete(T t) {
            root = delete(root, t);
        }

        private TreeNode delete(TreeNode x, T t) {
            if (x == null) {
                return null;
            }
            int cmp = t.compareTo(x.value);
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

        /**
         * 中序遍历(左-根-右): 排序二叉树的中序遍历可以得到一个有序序列
         * @param v 元素访问器接口(实现元素访问操作的解耦合)
         */
        public void inOrder(Visitor<T> v) {
            inOrder(root, v);
        }

        private void inOrder(TreeNode x, Visitor<T> v) {
            if (x != null) {
                inOrder(x.left, v);
                v.visit(x.value);
                inOrder(x.right, v);
            }
        }

        /**
         * 先序遍历(根-左-右)
         * @param v 树元素访问器接口
         */
        public void preOrder(Visitor<T> v) {
            preOrder(root, v);
        }

        private void preOrder(TreeNode x, Visitor<T> v) {
            if (x != null) {
                v.visit(x.value);
                preOrder(x.left, v);
                preOrder(x.right, v);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = {54, 38, 76, 62, 38, 26, 40, 83, 50, 98, 80};
        BST<Integer> tree = new BST<>();
        for (int i = 0; i < a.length; i++) {
            tree.add(a[i]);
        }
        printTree(tree);
        tree.delete(54);
        printTree(tree);
        tree.delete(38);
        printTree(tree);
    }

    private static void printTree(BST<Integer> tree) {
        System.out.println("先序: ");
        tree.preOrder(new Visitor<Integer>() {
            @Override
            public void visit(Integer t) {
                System.out.print(t + "\t");
            }
        });
        System.out.println();
        System.out.println("中序: ");
        tree.inOrder(new Visitor<Integer>() {
            @Override
            public void visit(Integer t) {
                System.out.print(t + "\t");
            }
        });
        System.out.println();
    }
}