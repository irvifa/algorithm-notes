package com.github.irvifa.algorithmnotes.courses.google.weekone.merge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Main {
    static class Pair implements Comparable<Pair> {
        public ArrayList<Integer> array;
        public int currentIndex;
        public int size;

        public Pair(ArrayList<Integer> array, int currentIndex, int size) {
            this.array = array;
            this.currentIndex = currentIndex;
            this.size = size;
        }

        @Override
        public int compareTo(Pair that) {
            return this.array.get(currentIndex) - that.array.get(that.currentIndex);
        }
    }

    // O(N log K)
    // k is number of arrays
    // N number of elements
    public static ArrayList<Integer> merge(ArrayList<ArrayList<Integer>> arrays, int[] arrayLengths) {
        // TODO: Implement the functionality here.
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        for (int i = 0; i < arrays.size(); i++) {
            Pair currentPair = new Pair(arrays.get(i), 0, arrays.get(i).size());
            queue.add(currentPair);
        }

        ArrayList<Integer> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            Pair currentPair = queue.poll();
            int currentIndex = currentPair.currentIndex;
            res.add(currentPair.array.get(currentIndex));
            if (currentPair.currentIndex < currentPair.size - 1) {
                queue.add(new Pair(currentPair.array, currentIndex + 1, currentPair.size));
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numArrays = Integer.parseInt(scanner.nextLine());
        int arrayLengths[] = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        ArrayList<ArrayList<Integer>> arrays = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < numArrays; ++i) {
            int[] array = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int el : array) arrayList.add(Integer.valueOf(el));
            arrays.add(arrayList);
        }
        scanner.close();

        // TODO: Implement the merge() function.
        ArrayList<Integer> merged = merge(arrays, arrayLengths);
        StringBuffer sb = new StringBuffer();
        for (int s : merged) {
            sb.append(s);
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }
}
