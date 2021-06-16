package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RolColSumTest {
    @Test
    public void whenCountSum() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[1].getRowSum(), is(6));
        assertThat(sums[2].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(3));
        assertThat(sums[1].getColSum(), is(6));
        assertThat(sums[2].getColSum(), is(9));
    }

    @Test
    public void whenCountAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums[0].getRowSum(), is(6));
        assertThat(sums[1].getRowSum(), is(6));
        assertThat(sums[2].getRowSum(), is(6));
        assertThat(sums[0].getColSum(), is(3));
        assertThat(sums[1].getColSum(), is(6));
        assertThat(sums[2].getColSum(), is(9));
    }
}
