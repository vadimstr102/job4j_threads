package ru.job4j.pools;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelArraySearchTest {
    Integer[] array = new Integer[100];

    @Before
    public void fillArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }

    @Test
    public void whenIndexFound() {
        int res = ParallelArraySearch.search(array, 55);
        assertThat(res, is(55));
    }

    @Test
    public void whenIndexNotFound() {
        int res = ParallelArraySearch.search(array, 666);
        assertThat(res, is(-1));
    }
}
