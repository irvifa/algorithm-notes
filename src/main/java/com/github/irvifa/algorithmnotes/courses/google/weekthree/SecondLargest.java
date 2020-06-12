package com.github.irvifa.algorithmnotes.courses.google.weekthree;

import java.util.Scanner;

public class SecondLargest {
    private static final int INVALID_INPUT = -1;
    private static final int ANSWER_IS_NOT_FOUND = -1;

    // Time Complexity: O(N)
    // Space Complexity: O(1)
    public static void secondLargest(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Input list size must be at least 2.");
        }
        int first, second;
        first = arr[0];
        second = Integer.MIN_VALUE;
        for (int value : arr) {
            if (value > first) {
                second = first;
                first = value;
            } else if (value > second && value != first) {
                second = value;
            }
        }
        if (second == Integer.MIN_VALUE) {
            second = ANSWER_IS_NOT_FOUND;
        }
        System.out.println(second);
    }

    public static int[] getInput(Scanner cin) {
        int size = cin.nextInt();
        if (size == 0) {
            throw new IllegalArgumentException("Input Size must be larger than 0.");
        }
        int[] inputArray = new int[size];
        for (int i = 0; i < size; i++) {
            inputArray[i] = cin.nextInt();
        }
        cin.close();
        return inputArray;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        try {
            int[] inputArray = getInput(cin);
            secondLargest(inputArray);
        } catch (IllegalArgumentException e) {
            System.out.println(INVALID_INPUT);
        } finally {
            cin.close();
        }

    }
}
