package com.example.algorithm.sortalgorithm;

/**
 * Created by alan on 2017/12/6.
 */

public class MySort {


    public static void quickSort(int[] numbers) {
        long start = System.currentTimeMillis();

        quickSort(numbers, 0, numbers.length - 1);

        System.out.print("快速排序，用时:" + (System.currentTimeMillis() - start) + ",");
        printArray(numbers);
    }

    /*
     *  快速排序
     *  对于排序来说，通常我们肯定有一个输入的数组作为参数，然后，快排是一个递归的过程，递归的话，只有
     *  一个数组作为函数的参数显然是不行的，然后这里是一个分治法的应用，因此需要有左右边界index，用作
     *  基准的数的index需不需要呢，答案是不需要，因为这个就是我们要返回的数据，根据这个数据来把数组逻
     *  辑上分割成两段，因此所需要的输入参数就出来了，数组，左右边界index
     *
     *  每一次排序，我们需要返回基准的数据，用于分割数组，然后递归，因此quickSort函数的代码就出来了，
     *  需要注意的是基准自身已经是最终的位置，所以不需要再到排序中去，加减1才是所需要的边界
     *
     *  在一次排序中，输入的参数依然不变，整个过程类似于一个挖坑填坑的过程，比如我们选了左边界作为基准
     *  点，然后这个数据被缓存下来，这样就有了一个坑，后续到要交换的时候，只需要把值赋给这个位置就好，
     *  赋值过来后，这个坑就转移到赋值过来的那个地方。需要注意的是比较的时候要考虑值相等的情况，避免值
     *  相等的时候会来回跳死循环
     *
     *  最后这个空位置是留给基准的，排序完成后，这个位置就是基准的最终位置，赋值过去就完成了一次排序，
     *  返回基准的位置给递归去调用
     *
     */
    private static void quickSort(int[] numbers, int l, int h) {
        if (l >= h) {
            return;
        }
        int index = partition(numbers, l, h);
        quickSort(numbers, l, index - 1);
        quickSort(numbers, index + 1, h);
    }

    private static int partition(int[] numbers, int l, int h) {
        int povit = numbers[l];
        while (l < h) {
            while (numbers[h] >= povit && l < h) {
                h--;
            }
            numbers[l] = numbers[h];
            while (numbers[l] <= povit && l < h) {
                l++;
            }
            numbers[h] = numbers[l];
        }
        numbers[l] = povit;
        return l;
    }

    public static void mergeSort(int[] numbers) {
        long start = System.currentTimeMillis();

        quickSort(numbers, 0, numbers.length - 1);

        System.out.print("归并排序，用时:" + (System.currentTimeMillis() - start) + ",");
        printArray(numbers);
    }

    /*
     *  归并排序
     *  快速排序是先排序，拿到基准的位置，用于分割数组，而归并排序是先逻辑分割，然后合并
     *  在每一次合并的时候，因为只是逻辑分割，所以除了左右边界外，还需要一个index来区别
     *  两个数组的边界，之后的过程则比较简单，建立一个缓存的数组，合并后的值赋值过去，再
     *  覆盖回来，完成一次合并
     * @param numbers
     * @param left
     * @param right
     */
    private static void mergeSort(int[] numbers, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = (left + right) / 2;
        mergeSort(numbers, left, middle);
        mergeSort(numbers, middle + 1, right);
        merge(numbers, left, middle, right);
    }

    private static void merge(int[] numbers, int left, int middle, int right) {
        int lp = left;
        int rp = middle + 1;
        int tp = left;
        int[] temp = new int[right - left];

        while (lp <= middle && rp <= right) {
            if (numbers[lp] <= numbers[rp]) {
                temp[tp++] = numbers[lp++];
            } else {
                temp[tp++] = numbers[rp++];
            }
        }
        while (lp <= middle) {
            temp[tp++] = numbers[lp++];
        }
        while (rp <= right) {
            temp[tp++] = numbers[rp++];
        }
        for (int i = left; i <= right; i++) {
            numbers[i] = temp[i];
        }
    }

    public static void printArray(int[] arr) {
        for (int x : arr) {
            System.out.print(" " + x);
        }
        System.out.println();
    }


    public static void heapSort(int[] numbers) {
        long start = System.currentTimeMillis();

        buildHeap(numbers);
        for (int i = numbers.length - 1; i >= 0; i--) {
            swap(numbers, 0, i);
            heapAdjust(numbers, 0, i);
        }

        System.out.print("堆  排序，用时:" + (System.currentTimeMillis() - start) + ",");
        printArray(numbers);
    }

    private static void buildHeap(int[] data) {
        int index = (data.length - 1) / 2;
        for (int i = index; i >= 0; i--) {
            //从一半的地方开始调整直到根节点，因为这里开始有子节点了
            heapAdjust(data, i, data.length);
        }
    }

    /**
     * 调整某一个位置的节点，如果有交换，从交换的地方继续调整，直到完成，算是
     * 这个节点的调整完毕
     *
     * @param data
     * @param index
     */
    private static void heapAdjust(int[] data, int index, int length) {
        int lastIndex = length - 1;
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int max = index;
        if (left > lastIndex) { //没有子节点

        } else if (right > lastIndex) { //有一个左子节点
            if (data[index] < data[left]) {
                swap(data, index, left);
                max = left;
            }
        } else {  //有两个子节点
            if (data[left] < data[right]) {
                if (data[index] < data[right]) {
                    swap(data, index, right);
                    max = right;
                }
            } else {
                if (data[index] < data[left]) {
                    swap(data, index, left);
                    max = left;
                }
            }
        }
        if (max != index) {
            heapAdjust(data, max, length);
        }
    }

    private static void swap(int[] data, int i, int j) {
        if (i == j) {
            return;
        }
        data[i] = data[i] + data[j];
        data[j] = data[i] - data[j];
        data[i] = data[i] - data[j];
    }
}
