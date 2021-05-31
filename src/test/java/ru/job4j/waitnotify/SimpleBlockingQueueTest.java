package ru.job4j.waitnotify;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenPollAsManyTimesAsOffer() throws InterruptedException {
        List<Integer> result = new ArrayList<>();
        List<Integer> expected = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            sbq.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        try {
                            result.add(sbq.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        assertEquals(result, expected);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenConsumerDoesNotKnowTheNumberOfItems() throws InterruptedException {
        List<Integer> result = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i <= 10; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            result.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }
}
