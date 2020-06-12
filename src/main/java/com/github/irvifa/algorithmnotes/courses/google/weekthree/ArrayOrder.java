package com.github.irvifa.algorithmnotes.courses.google.weekthree;

import java.util.Scanner;

enum SortedOrder {
    ASCENDING(1), DESCENDING(-1), UNORDERED(0);
    private final int value;

    SortedOrder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

public class ArrayOrder {
    private static SortedOrder getOrder(int[] inputArray, int prev, int current) {
        if (inputArray[prev] <= inputArray[current]) {
            return SortedOrder.ASCENDING;
        } else if (inputArray[prev] > inputArray[current]) {
            return SortedOrder.DESCENDING;
        }
        return SortedOrder.UNORDERED;
    }

    // Time Complexity: O(N)
    // Space Complexity: O(1)
    public static int checkArrayOrder(int[] inputArray) {
        int size = inputArray.length;
        if (size == 0 || size == 1)
            return 0;

        SortedOrder currentSortedOrder = getOrder(inputArray, 0, 1);
        SortedOrder prevSortedOrder = getOrder(inputArray, 0, 1);
        for (int i = 1; i < size; i++) {
            currentSortedOrder = getOrder(inputArray, i - 1, i);
           if (prevSortedOrder != currentSortedOrder) {
                return SortedOrder.UNORDERED.getValue();
            }
            prevSortedOrder = currentSortedOrder;
        }
        return currentSortedOrder.getValue();
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int size = cin.nextInt();
        int[] inputArray = new int[size];

        for (int i = 0; i < size; i++) {
            inputArray[i] = cin.nextInt();
        }

        cin.close();

        int sortedOrder = checkArrayOrder(inputArray);
        System.out.println(sortedOrder);
    }
}
