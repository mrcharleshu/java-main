package com.charles.algorithm;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 快速排序算法
 */
public class QuickSort<E extends Comparable<E>> extends Sort<E> {

    /**
     * 递归快速排序实现
     * @param array      待排序数组
     * @param low        低指针
     * @param high       高指针
     * @param comparator 比较器
     */
    public void quickSort(E[] array, int low, int high, Comparator<E> comparator) {
        /*
         * 如果分区中的低指针小于高指针时循环；
         * 如果low = high，无需再处理；
         * 如果low > high，则说明上次枢纽元素的位置pivot就是low或者是high，此种情况下分区不存，也不需处理
         */
        if (low < high) {
            //对分区进行排序整理 
            int pivot = partition1(array, low, high, comparator);
            /*
             * 以pivot为边界，把数组分成三部分[low, pivot - 1]、[pivot]、[pivot + 1, high]
             * 其中[pivot]为枢纽元素，不需处理，再对[low, pivot - 1]与[pivot + 1, high]
             * 各自进行分区排序整理与进一步分区
             */
            quickSort(array, low, pivot - 1, comparator);
            quickSort(array, pivot + 1, high, comparator);
        }

    }

    /**
     * 实现一
     * @param array      待排序数组
     * @param low        低指针
     * @param high       高指针
     * @param comparator 比较器
     * @return int 调整后中枢位置
     */
    private int partition1(E[] array, int low, int high, Comparator<E> comparator) {
        E pivot = array[low];//以第一个元素为中枢元素
        //从前向后依次指向比中枢元素小的元素，刚开始时指向中枢，也是小于与大小中枢的元素的分界点 
        int border = low;
        /*
         * 在中枢元素后面的元素中查找小于中枢元素的所有元素，并依次从第二个位置从前往后存放
         * 注，这里最好使用i来移动，如果直接移动low的话，最后不知道数组的边界了，但后面需要
         * 知道数组的边界
         */
        for (int i = low + 1; i <= high; i++) {
            //如果找到一个比中枢元素小的元素 
            if (comparator.compare(array[i], pivot) < 0) {
                swap(array, ++border, i);//border前移，表示有小于中枢元素的元素 
            }
        } 
        /*
         * 1、如果border没有移动时说明说明后面的元素都比中枢元素要大，border与low相等，此时是同一位置交换，是否交换都没关系；
         * 2、当border移到了high时说明所有元素都小于中枢元素，此时将中枢元素与最后一个元素交换即可，即low与high进行交换，
         * 大的中枢元素移到了序列最后；
         * 3、如果 low < minIndex < high，表明中枢后面的元素前部分小于中枢元素，而后部分大于中枢元素，
         * 此时中枢元素与前部分数组中最后一个小于它的元素交换位置，使得中枢元素放置在正确的位置
         */
        if (border != low) {
            swap(array, border, low);
        }
        return border;
    }

    /**
     * 实现二
     * @param array 待排序数组
     * @param low   待排序区低指针
     * @param high  待排序区高指针
     * @param c     比较器
     * @return int 调整后中枢位置
     */
    private int partition2(E[] array, int low, int high, Comparator<E> c) {
        int pivot = low;//中枢元素位置，我们以第一个元素为中枢元素 
        //退出条件这里只可能是 low = high 
        while (true) {
            if (pivot != high) {//如果中枢元素在低指针位置时，我们移动高指针 
                //如果高指针元素小于中枢元素时，则与中枢元素交换 
                if (c.compare(array[high], array[pivot]) < 0) {
                    swap(array, high, pivot);
                    //交换后中枢元素在高指针位置了 
                    pivot = high;
                } else {//如果未找到小于中枢元素，则高指针前移继续找 
                    high--;
                }
            } else {//否则中枢元素在高指针位置 
                //如果低指针元素大于中枢元素时，则与中枢元素交换 
                if (c.compare(array[low], array[pivot]) > 0) {
                    swap(array, low, pivot);
                    //交换后中枢元素在低指针位置了 
                    pivot = low;
                } else {//如果未找到大于中枢元素，则低指针后移继续找 
                    low++;
                }
            }
            if (low == high) {
                break;
            }
        }
        //返回中枢元素所在位置，以便下次分区 
        return pivot;
    }

    /**
     * 实现三
     * @param array 待排序数组
     * @param low   待排序区低指针
     * @param high  待排序区高指针
     * @param c     比较器
     * @return int 调整后中枢位置
     */
    private int partition3(E[] array, int low, int high, Comparator<E> c) {
        int pivot = low;//中枢元素位置，我们以第一个元素为中枢元素 
        low++;
        //----调整高低指针所指向的元素顺序，把小于中枢元素的移到前部分，大于中枢元素的移到后面部分 
        //退出条件这里只可能是 low = high 

        while (true) {
            //如果高指针未超出低指针 
            while (low < high) {
                //如果低指针指向的元素大于或等于中枢元素时表示找到了，退出，注：等于时也要后移 
                if (c.compare(array[low], array[pivot]) >= 0) {
                    break;
                } else {//如果低指针指向的元素小于中枢元素时继续找 
                    low++;
                }
            }

            while (high > low) {
                //如果高指针指向的元素小于中枢元素时表示找到，退出 
                if (c.compare(array[high], array[pivot]) < 0) {
                    break;
                } else {//如果高指针指向的元素大于中枢元素时继续找 
                    high--;
                }
            }
            //退出上面循环时 low = high 
            if (low == high) {
                break;
            }

            swap(array, low, high);
        }

        //----高低指针所指向的元素排序完成后，还得要把中枢元素放到适当的位置 
        if (c.compare(array[pivot], array[low]) > 0) {
            //如果退出循环时中枢元素大于了低指针或高指针元素时，中枢元素需与low元素交换 
            swap(array, low, pivot);
            pivot = low;
        } else if (c.compare(array[pivot], array[low]) <= 0) {
            swap(array, low - 1, pivot);
            pivot = low - 1;
        }

        //返回中枢元素所在位置，以便下次分区 
        return pivot;
    }

    public static void main(String[] args) {
        Integer[] arr = {7, 2, 4, 3, 12, 1, 9, 6, 8, 5, 11, 10};
        QuickSort<Integer> instance = new QuickSort<>();
        int low = 0, high = arr.length - 1;
        instance.quickSort(arr, low, high, Integer::compare);
        // instance.quickSort(arr, low, high, (o1, o2) -> o2 - o1);
        Arrays.stream(arr).forEach(System.out::println);
    }
}