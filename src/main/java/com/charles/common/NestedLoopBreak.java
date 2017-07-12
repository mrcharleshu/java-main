package com.charles.common;

/**
 * 在Java中，如何跳出当前的多重嵌套循环？
 * 在最外层循环前加一个标记如A，然后用break A;可以跳出多重循环。
 * <p/>
 * （Java中支持带标签的break和continue语句，作用有点类似于C和C++中的goto语句，
 * 但是就像要避免使用goto一样，应该避免使用带标签的break和continue，因为它不会让你的程序变得更优雅，
 * 很多时候甚至有相反的作用，所以这种语法其实不知道更好）
 */
public class NestedLoopBreak {

    public static void main(String[] args) {
        int arr[][] = {{1, 2, 3}, {4, 5, 6, 7}, {9}};
        boolean found = false;
        for (int i = 0; i < arr.length && !found; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.println("i =" + i + ",j =" + j);
                if (arr[i][j] == 5) {
                    found = true;
                    break;
                }
            }
        }
    }
}
