package com.charles.algorithm;

public class Queen8 {
    private static final int SPEC_NUM = 8;
    private static final int[] CHESS_POSITIONS = new int[SPEC_NUM];
    private int counter = 0;

    // 判断 row 行 col 列放置是否合适
    private boolean isOk(int row, int col) {
        int leftup = col - 1, rightup = col + 1;
        for (int i = row - 1; i >= 0; --i) { // 逐行往上考察每一行
            if (CHESS_POSITIONS[i] == col) return false; // 第 i 行的 col 列有棋子吗？
            if (leftup >= 0) { // 考察左上对角线：第 i 行 leftup 列有棋子吗？
                if (CHESS_POSITIONS[i] == leftup) return false;
            }
            if (rightup < 8) { // 考察右上对角线：第 i 行 rightup 列有棋子吗？
                if (CHESS_POSITIONS[i] == rightup) return false;
            }
            --leftup;
            ++rightup;
        }
        return true;

    }

    private void settleQueen(int row) {
        if (row == SPEC_NUM) { // 8 个棋子都放置好了，打印结果
            printChessBoard();
            counter++;
            return; // 8 行棋子都放好了，已经没法再往下递归了，所以就 return
        }
        for (int col = 0; col < SPEC_NUM; ++col) { // 每一行都有 8 中放法
            if (isOk(row, col)) { // 有些放法不满足要求
                CHESS_POSITIONS[row] = col; // 第 row 行的棋子放到了 col 列
                settleQueen(row + 1); // 考察下一行
            }
        }
    }

    private void printChessBoard() {
        System.out.println("---------------");
        for (int row = 0; row < SPEC_NUM; ++row) {
            for (int col = 0; col < SPEC_NUM; ++col) {
                if (CHESS_POSITIONS[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    private int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.settleQueen(0);
        System.out.println(String.format("counter = %d", queen8.getCounter()));
    }

}
