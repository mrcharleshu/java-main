package com.charles.algorithm.backtracking;

public class Queen8 {
    private int maxNum;
    /**
     * 全局或成员变量, 下标表示行, 值表示 queen 存储在哪一列
     */
    private int[] positions;
    private String separator;
    private int counter = 0;

    private Queen8(int maxNum) {
        this.maxNum = maxNum;
        this.positions = new int[maxNum];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxNum * 2 - 1; i++) {
            sb.append("-");
        }
        this.separator = sb.toString();
    }

    // 判断 row 行 col 列放置是否合适
    private boolean isOk(int row, int col) {
        int leftUp = col - 1, rightUp = col + 1;
        for (int i = row - 1; i >= 0; --i) { // 逐行往上考察每一行
            if (positions[i] == col) {
                return false; // 第 i 行的 col 列有棋子吗？
            }
            if (leftUp >= 0) { // 考察左上对角线：第 i 行 leftup 列有棋子吗？
                if (positions[i] == leftUp) {
                    return false;
                }
            }
            if (rightUp < maxNum) { // 考察右上对角线：第 i 行 rightup 列有棋子吗？
                if (positions[i] == rightUp) {
                    return false;
                }
            }
            --leftUp;
            ++rightUp;
        }
        return true;

    }

    private void settleQueen(int row) {
        if (row == maxNum) { // 8 个棋子都放置好了，打印结果
            printChessBoard();
            counter++;
            return; // 8 行棋子都放好了，已经没法再往下递归了，所以就 return
        }
        for (int col = 0; col < maxNum; ++col) { // 每一行都有 8 中放法
            if (isOk(row, col)) { // 有些放法不满足要求
                positions[row] = col; // 第 row 行的棋子放到了 col 列
                settleQueen(row + 1); // 考察下一行
            }
        }
    }

    private void printChessBoard() {
        System.out.println(separator);
        for (int row = 0; row < maxNum; ++row) {
            for (int col = 0; col < maxNum; ++col) {
                if (positions[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println(separator);
    }

    private void listAll() {
        settleQueen(0);
        System.out.println(String.format("counter = %d", counter));
    }

    public static void main(String[] args) {
        long start_millis = System.currentTimeMillis();
        new Queen8(8).listAll();
        System.out.println(String.format("cost %d millis", System.currentTimeMillis() - start_millis));
    }
}
