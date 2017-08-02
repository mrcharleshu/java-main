package com.charles.algorithm.tree;

/**
 * 把树的节点应该在树形打印中的坐标先计算好，然后按照 row,col 对坐标进行排序方便打印
 */
public class Point implements Comparable<Point> {

    Point(int row, int col, int data) {
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