package ru.job4j.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    @GuardedBy("this")
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int total = 100;
        CountBarrier countBarrier = new CountBarrier(total);
        Thread threadCount = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started.");
                    int i = 0;
                    while (i < total) {
                        countBarrier.count();
                        i++;
                    }
                    System.out.println(Thread.currentThread().getName() + " finished.");
                },
                "ThreadCount"
        );
        Thread threadAwait = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started.");
                    countBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " finished.");
                },
                "ThreadAwait"
        );
        threadCount.start();
        threadAwait.start();
    }
}
