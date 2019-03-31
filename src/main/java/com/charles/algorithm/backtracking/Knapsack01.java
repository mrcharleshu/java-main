package com.charles.algorithm.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 我们有一个背包，背包总的承载重量是 Wkg。现在我们有 n 个物品，每个物品的重量不等，并且不可分割。
 * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，如何让背包中物品的总重量最大？
 * <p>
 * 我们可以把物品依次排列，整个问题就分解为了 n 个阶段，
 * 每个阶段对应一个物品怎么选择。先对第一个物品进行处理，选择装进去或者不装进去，然后再递归地处理剩下的物品。
 * <p>
 * 日志输出能表明整个穷举的思路
 */
public class Knapsack01 {
    private static final String EMPTY_SPACE_4 = "    ";
    private Map<Integer, int[]> resultsMap = new HashMap<>();
    private int[] selections;
    private int maxW = Integer.MIN_VALUE; // 存储背包中物品总重量的最大值
    private int[] items; // 表示每个物品的重量
    private int n; // 表示物品个数
    private int w; // 背包承受的最大重量

    private Knapsack01(int[] items, int w) {
        this.items = items;
        this.n = items.length;
        this.w = w;
        this.selections = new int[this.n];
    }

    private String getSpaces(int which) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < which; i++) {
            sb.append(EMPTY_SPACE_4);
        }
        return sb.toString();
    }

    private void printAllSolution() {
        for (Map.Entry<Integer, int[]> entry : resultsMap.entrySet()) {
            System.out.println(entry.getKey() + " | " + Arrays.toString(entry.getValue()));
        }
    }

    private void printOptimalSolution() {
        int maxW = Collections.max(resultsMap.keySet());
        System.out.println(String.format("最优解[%d]：%s", maxW, Arrays.toString(resultsMap.get(maxW))));
    }

    /**
     * @param i  表示考察到哪个物品了
     * @param cw 表示当前已经装进去的物品的重量和
     */
    public void f(int i, int cw) {
        if (cw == w || i == n) { // cw==w 表示装满了 ;i==n 表示已经考察完所有的物品
            if (cw > maxW) {
                maxW = cw;
                resultsMap.put(maxW, Arrays.copyOf(selections, n));
                System.out.println(String.format("总重量：%d", maxW));
                System.out.println("------------------------------");
            } else {
                System.err.println(String.format("当前总重量：%d 小于之前总重量：%d", cw, maxW));
                System.err.println("..............................");
            }
            return;
        }
        System.out.println(String.format("%s不把第 %d 个物品[%d]装进去", getSpaces(i), i + 1, items[i]));
        selections[i] = 0;
        f(i + 1, cw); // 不把第i个物品装进去
        System.out.println(String.format("%s把第 %d 个物品[%d]装进去", getSpaces(i), i + 1, items[i])
                + ((cw + items[i] <= w) ? "" : "【包已满，这个放不进去了】"));
        if (cw + items[i] <= w) {// 已经超过可以背包承受的重量的时候，就不要再装了
            selections[i] = items[i];
            f(i + 1, cw + items[i]); // 把第i个物品装进去
        }
    }

    public static void main(String[] args) {
        // int[] items = new int[]{19, 31, 40, 35, 28, 23, 12, 7};
        int[] items = new int[]{19, 31, 40, 35};
        Knapsack01 knapsack01 = new Knapsack01(items, 100);
        knapsack01.f(0, 0);
        knapsack01.printAllSolution();
        knapsack01.printOptimalSolution();
    }
}
