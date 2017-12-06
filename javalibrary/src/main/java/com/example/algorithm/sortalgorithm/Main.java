package com.example.algorithm.sortalgorithm;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        int length = 20;
        int[] numbers = new int[length];
        for (int i = 0; i < length; i++) {
            numbers[i] = random.nextInt(100);
        }

        int[] num = numbers.clone();
        int[] num2 = numbers.clone();

        MySort.quickSort(numbers);
        MySort.mergeSort(num);
        MySort.heapSort(num2);
    }


}
