package ru.job4j.pools;

import ru.job4j.waitnotify.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is run...");
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " has terminated.");
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            try {
                thread.interrupt();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        Thread.sleep(1000);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            pool.work(() -> System.out.print(finalI));
        }
        Thread.sleep(1000);
        System.out.println(System.lineSeparator() + "Shutdown...");
        pool.shutdown();
        System.out.println("Shutdown done.");
    }
}
