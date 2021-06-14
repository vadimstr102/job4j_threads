package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelArraySearch<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final V value;
    private final int from;
    private final int to;

    private ParallelArraySearch(V[] array, V value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public static <V> int search(V[] array, V value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelArraySearch<>(array, value, 0, array.length - 1));
    }

    @Override
    protected Integer compute() {
        if (to - from + 1 <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelArraySearch<V> leftSearch = new ParallelArraySearch<>(array, value, from, mid);
        ParallelArraySearch<V> rightSearch = new ParallelArraySearch<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    private Integer linearSearch() {
        int res = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                res = i;
                break;
            }
        }
        return res;
    }
}
