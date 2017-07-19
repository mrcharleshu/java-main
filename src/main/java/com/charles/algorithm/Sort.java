package com.charles.algorithm;

public class Sort<E> {

    protected void swap(E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
